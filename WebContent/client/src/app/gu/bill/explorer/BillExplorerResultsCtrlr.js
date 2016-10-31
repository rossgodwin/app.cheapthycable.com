define([
	'app/res/AppConsts',
	'common/pagination/PaginationCtrlr'
], function(
	appConsts,
	PaginationCtrlr) {
	return ['$window', '$scope', 'spinnerService', 'billExplorerData', 'BillHttpService', 'fbHlprPrvdr', 'twitterHlprPrvdr', ctrlr];
	
	function ctrlr($window, $scope, spinnerService, data, BillHttpService, fbHlprPrvdr, twitterHlprPrvdr) {
		var vm = this;
		var watchCount = 0;
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
			addWatchs();
		}
		
		function addWatchs() {
			$scope.$watch(function () { return data.critrMileRadius; }, function (critrMileRadius) {
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
			
			$scope.$watch(function () { return data.critrServicesSetOperator; }, function (critrServicesSetOperator) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrInternetService; }, function (critrInternetService) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrCableService; }, function (critrCableService) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrPhoneService; }, function (critrPhoneService) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrCableOptionsBoxCount; }, function (critrCableOptionsBoxCount) {
				setAllBillsPage(1);
			}, true);
			
			$scope.$watch(function () { return data.critrCableOptionsDvrCount; }, function (critrCableOptionsDvrCount) {
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
			
			var critr = {
				zipCode : data.critrZipCode,
				mileRadius : data.critrMileRadius,
				exactTotalAmount : data.critrExactTotalAmount,
				providerId : data.critrProvider ? data.critrProvider.id : null,
				matchProviderName : data.critrProviderName,
				servicesSetOperator : data.critrServicesSetOperator,
				internetService : data.critrInternetService,
				cableService : data.critrCableService,
				phoneService : data.critrPhoneService,
				sorts : [{option : data.critrSort.option, order : data.critrSort.order}]
			};
			
//			if (data.critrInternetService) {
//				critr.internetService = data.critrInternetService;
//			}
//			if (data.critrCableService) {
//				critr.cableService = data.critrCableService;
//			}
//			if (data.critrPhoneService) {
//				critr.phoneService = data.critrPhoneService;
//			}
			
			if (data.critrCableService) {
				if (data.critrCableOptionsBoxCount != data.cableOptionsBoxNAOption) {
					critr.cableOptionBoxCount = data.critrCableOptionsBoxCount;
				}
				if (data.critrCableOptionsDvrCount != data.cableOptionsDvrNAOption) {
					critr.cableOptionDvrCount = data.critrCableOptionsDvrCount;
				}
			}
			
			BillHttpService.getBillsListPage(critr, paginationCtrlr.startPage, vm.pageSize).then(function(response) {
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