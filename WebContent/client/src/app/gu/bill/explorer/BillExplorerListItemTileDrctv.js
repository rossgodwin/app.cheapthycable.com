define([
	'app/utils/BillUtils'
], function(BillUtils) {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				index : '=',
				bill : '='
			},
			templateUrl : "client/src/app/gu/bill/explorer/bill-explorer-list-item-tile.drctv.tpl.html",
			controller : ['billExplorerData', ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr(data) {
		var vm = this;
		
		// public variables
		vm.commentsExpanded = false;
		
		// public functions
		vm.getTotalAmountStyleClass = getTotalAmountStyleClass;
		vm.amountRelativeToAvg = amountRelativeToAvg;
		vm.isTotalAmountUnderAvg = isTotalAmountUnderAvg;
		vm.isTotalAmountOverAvg = isTotalAmountOverAvg;
		vm.getServicesStr = getServicesStr;
		vm.getAllOptionsStr = getAllOptionsStr;
		vm.tglCommentsExpanded = tglCommentsExpanded;
		
		function getTotalAmountStyleClass() {
			return {
		    	'app-bill-totalAmount-under': isTotalAmountUnderAvg(),
		    	'app-bill-totalAmount-over': isTotalAmountOverAvg()
		    };
		}
		
		function amountRelativeToAvg() {
			var diff = vm.bill.totalAmount - data.averageTotalAmount;
			if (diff > 0) {
				return diff;
			} else {
				return diff * -1;
			}
		}
		
		function isTotalAmountUnderAvg() {
			var r = vm.bill.totalAmount < data.averageTotalAmount;
			return r;
		}
		
		function isTotalAmountOverAvg() {
			var r = vm.bill.totalAmount > data.averageTotalAmount;
			return r;
		}
		
		function getServicesStr() {
			var r = BillUtils.getServicesStr(vm.bill, ', ');
			return r;
		}
		
		function getAllOptionsStr() {
			var r = BillUtils.getAllOptionsStr(vm.bill, ', ');
			return r;
		}
		
		function tglCommentsExpanded() {
			vm.commentsExpanded = !vm.commentsExpanded;
		}
	};
});