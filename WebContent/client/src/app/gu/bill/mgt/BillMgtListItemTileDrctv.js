define(['src/app/utils/BillUtils'], function(BillUtils) {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				index : '=',
				bill : '=',
				deleteBill : '&'
			},
			templateUrl : "client/src/app/gu/bill/mgt/bill-mgt-list-item-tile.drctv.tpl.html",
			controller : ['$filter', 'fbHlprPrvdr', 'twitterHlprPrvdr', ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr($filter, fbHlprPrvdr, twitterHlprPrvdr) {
		var vm = this;
		
		// public variables
		vm.expanded = false;
		
		// public functions
		vm.tweet = tweet;
		vm.fbShare = fbShare;
		vm.getTotalAmountClasses = getTotalAmountClasses;
		vm.isTotalAmountUnderAvg = isTotalAmountUnderAvg;
		vm.isTotalAmountAvg = isTotalAmountAvg;
		vm.isTotalAmountOverAvg = isTotalAmountOverAvg;
		vm.getServicesStr = getServicesStr;
		vm.getAllOptionsStr = getAllOptionsStr;
		vm.amountRelativeToAvg = amountRelativeToAvg;
		
		init();
		
		function init() {
			if (vm.index === 0) {
				vm.expanded = true;
			}
		}
		
		function tweet() {
			return twitterHlprPrvdr.tweetUrlViaAppRefWebsite(bldTweet());
		}
		
		function bldTweet() {
			var ofstAmt = $filter('currency')(amountRelativeToAvg());
			var avgAmt = $filter('currency')(vm.bill.stats.averageTotalAmount);
			
			var rslt = 'I learned that my cable/sat bill is';
			if (vm.isTotalAmountUnderAvg()) {
				rslt += ' ' + ofstAmt + ' below the average ' + avgAmt;
			} else if (vm.isTotalAmountUnderAvg()) {
				rslt += ' the average amount ' + avgAmt;
			} else if (vm.isTotalAmountOverAvg()) {
				rslt += ' ' + ofstAmt + ' above the average ' + avgAmt;
			}
			rslt += ' in my area. Compare yours!';
			
			return rslt;
		}
		
		function fbShare() {
			fbHlprPrvdr.promote(bldFbPost());
		}
		
		function bldFbPost() {
			var ofstAmt = $filter('currency')(amountRelativeToAvg());
			var avgAmt = $filter('currency')(vm.bill.stats.averageTotalAmount);
			
			var rslt = 'I learned that my cable/satellite bill is';
			if (vm.isTotalAmountUnderAvg()) {
				rslt += ' ' + ofstAmt + ' below the average ' + avgAmt;
			} else if (vm.isTotalAmountUnderAvg()) {
				rslt += ' the average amount ' + avgAmt;
			} else if (vm.isTotalAmountOverAvg()) {
				rslt += ' ' + ofstAmt + ' above the average ' + avgAmt;
			}
			rslt += ' in my area. You to can see how your cable/satellite bill compares with others!';
			
			return rslt;
		}
		
		function amountRelativeToAvg() {
			var diff = vm.bill.totalAmount - vm.bill.stats.averageTotalAmount;
			if (diff > 0) {
				return diff;
			} else {
				return diff * -1;
			}
		}
		
		function getTotalAmountClasses() {
			return {
		    	'app-bill-totalAmount-under': isTotalAmountUnderAvg(),
		    	'app-bill-totalAmount-over': isTotalAmountOverAvg()
		    };
		}
		
		function isTotalAmountUnderAvg() {
			var r = vm.bill.totalAmount < vm.bill.stats.averageTotalAmount;
			return r;
		}
		
		function isTotalAmountAvg() {
			var r = vm.bill.totalAmount === vm.bill.stats.averageTotalAmount;
			return r;
		}
		
		function isTotalAmountOverAvg() {
			var r = vm.bill.totalAmount > vm.bill.stats.averageTotalAmount;
			return r;
		}
		
		function getServicesStr() {
			return BillUtils.getServicesStr(vm.bill, ', ');
		}
		
		function getAllOptionsStr() {
			var r = BillUtils.getAllOptionsStr(vm.bill, ', ');
			return r;
		}
	};
});