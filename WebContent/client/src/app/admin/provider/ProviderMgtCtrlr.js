define([
	'src/app/res/AppConsts',
	'src/common/pagination/PaginationCtrlr'
], function(
	appConsts,
	PaginationCtrlr) {
	return ['$state', 'spinnerService', 'ProviderHttpService', ctrlr];
	
	function ctrlr($state, spinnerService, ProviderHttpService) {
		var vm = this;
		
		var paginationCtrlr = new PaginationCtrlr();
		
		// public variables
		vm.titlebarText = 'Providers';
		vm.providers = [];
		vm.providersCurrentPage = 0;
		vm.providersPageNumbers = [];
		vm.providersTotalPages = 0;
		
		// public functions
		vm.setProvidersPage = setProvidersPage;
		vm.titlebarBack = back;
		
		init();
		
		function init() {
			setProvidersPage(1);
		}
		
		function setProvidersPage(pageNumber) {
			var pageSize = 40;
			
			if (pageNumber < 1) {
				return;
			}
			
			paginationCtrlr.update(vm.providers.length, pageNumber, pageSize);
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			ProviderHttpService.getProviderListPage(vm.searchProviderName, paginationCtrlr.startPage, pageSize).then(function(response) {
				var page = response.data.result;
				vm.providers = page.records;
				paginationCtrlr.update(page.total, pageNumber, pageSize);
				
				vm.providersCurrentPage = paginationCtrlr.currentPage;
				vm.providersPageNumbers = paginationCtrlr.pages;
				vm.providersTotalPages = paginationCtrlr.totalPages;
				
				spinnerService.hide(appConsts.screenLoadingSpinner);
			}).catch(function(error) {
				// TODO
			});
		}
		
		function back() {
			$state.go('app.admin.dashboard');
		}
	};
});