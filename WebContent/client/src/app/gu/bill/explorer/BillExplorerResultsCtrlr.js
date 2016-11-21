define([
	'src/app/res/AppConsts',
	'src/common/pagination/PaginationCtrlr'
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
			loadBillStats();
		}
		
		function addWatchs() {
			$scope.$watch(function () { return data.critrMileRadius; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrExactTotalAmount; }, watchChangeListener2, true);
			$scope.$watch(function () { return data.critrProvider; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrProviderName; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrServicesSetOperator; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrInternetService; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrCableService; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrPhoneService; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrCableOptionsBoxCount; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrCableOptionsDvrCount; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrCableOptionsSpecialChannels; }, watchChangeListener1, true);
			$scope.$watch(function () { return data.critrSort; }, watchChangeListener1, true);
		}
		
		function watchChangeListener1(newValue, oldValue) {
			if (newValue !== oldValue) {
				loadBillStats();
			}
		}
		
		function watchChangeListener2(newValue, oldValue) {
			if (newValue !== oldValue) {
				setAllBillsPageSpinnerCtrl(1, true);
			}
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
		
		function buildCritr() {
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
			
			if (data.critrCableService) {
				if (data.critrCableOptionsBoxCount != data.cableOptionsBoxNAOption) {
					critr.cableOptionBoxCount = data.critrCableOptionsBoxCount;
				}
				if (data.critrCableOptionsDvrCount != data.cableOptionsDvrNAOption) {
					critr.cableOptionDvrCount = data.critrCableOptionsDvrCount;
				}
				if (data.critrCableOptionsSpecialChannels && data.critrCableOptionsSpecialChannels) {
					critr.cableOptionSpecialChannels = data.critrCableOptionsSpecialChannels;
				}
			}
			
			return critr;
		}
		
		function loadBillStats() {
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			var critr = buildCritr();
			critr.exactTotalAmount = null;
			
			BillHttpService.getBillExplorerStats(critr).then(function(response) {
				var result = response.data.result;
				
				data.lowestTotalAmount = result.lowestTotalAmount;
				data.averageTotalAmount = result.averageTotalAmount;
				data.highestTotalAmount = result.highestTotalAmount;
				
				setAllBillsPageSpinnerCtrl(1, false);
			}).catch(function(error) {
				// TODO
			});
		}
		
		function setAllBillsPage(pageNumber) {
			setAllBillsPageSpinnerCtrl(pageNumber, true);
		}
		
		function setAllBillsPageSpinnerCtrl(pageNumber, showSpinner) {
			if (pageNumber < 1) {
				return;
			}
			
			if (showSpinner) {
				spinnerService.show(appConsts.screenLoadingSpinner);
			}
			
			paginationCtrlr.update(vm.allBills.length, pageNumber, vm.pageSize);
			
			var critr = buildCritr();
			
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