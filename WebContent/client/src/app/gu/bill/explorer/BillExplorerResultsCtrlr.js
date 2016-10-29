define([
	'app/res/AppConsts',
	'common/pagination/PaginationCtrlr'
], function(
	appConsts,
	PaginationCtrlr) {
	return ['$window', '$scope', 'spinnerService', 'billExplorerData', 'BillHttpService', 'fbHlprPrvdr', 'twitterHlprPrvdr', ctrlr];
	
	function ctrlr($window, $scope, spinnerService, data, BillHttpService, fbHlprPrvdr, twitterHlprPrvdr) {
		var vm = this;
		
		var paginationCtrlr = new PaginationCtrlr();
		
		// public variables
		vm.pageSize = 15;
		vm.allBills = [];
		vm.allBillsCurrentPage = 0;
		vm.allBillsPageNumbers = [];
		vm.allBillsTotalPages = 0;
		
		// public functions
		vm.tweet = tweet;
		vm.fbShare = fbShare;
		vm.scrollToTop = scrollToTop;
		vm.setAllBillsPage = setAllBillsPage;
		
		init();
		
		function init() {
			$scope.$watch(function () { return data.mileRadius; }, function (mileRadius) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrExactTotalAmount; }, function (critrExactTotalAmount) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrProvider; }, function (critrProvider) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrProviderName; }, function (critrProviderName) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrSort; }, function (critrSort) {
				setAllBillsPage(1);
			}, true);
		}
		
		function tweet() {
			return twitterHlprPrvdr.tweetUrlViaAppRefWebsite('I searched what others in my area are paying for cable, internet and phone for FREE!');
		}
		
		function fbShare() {
			fbHlprPrvdr.promote('I signed up for this FREE website that allows me to see what others are paying for cable/satellite. Give it a try!');
		}
		
		function scrollToTop() {
			$window.scrollTo(0, angular.element(document.getElementById('billExplorerSearch')).offsetTop);
		}
		
		function setAllBillsPage(pageNumber) {
			if (pageNumber < 1) {
				return;
			}
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			paginationCtrlr.update(vm.allBills.length, pageNumber, vm.pageSize);
			
			var criteria = {
				zipCode : data.zipCode,
				mileRadius : data.mileRadius,
				exactTotalAmount : data.critrExactTotalAmount,
				providerId : data.critrProvider ? data.critrProvider.id : null,
				matchProviderName : data.critrProviderName,
				sorts : [{option : data.critrSort.option, order : data.critrSort.order}]
			};
			
			BillHttpService.getBillsListPage(criteria, paginationCtrlr.startPage, vm.pageSize).then(function(response) {
				var page = response.data.result;
				vm.allBills = page.records;
				paginationCtrlr.update(page.total, pageNumber, vm.pageSize);
				
				vm.allBillsCurrentPage = paginationCtrlr.currentPage;
				vm.allBillsPageNumbers = paginationCtrlr.pages;
				vm.allBillsTotalPages = paginationCtrlr.totalPages;
				
				spinnerService.hide(appConsts.screenLoadingSpinner);
			}).catch(function(error) {
				// TODO
			});
		}
	}
});