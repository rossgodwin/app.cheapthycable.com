define([
	'src/app/res/AppConsts',
	'src/common/pagination/PaginationCtrlr'
], function(
	appConsts,
	PaginationCtrlr) {
	return ['$state', 'spinnerService', 'BaAlertHttpService', '$uibModal', ctrlr];
	
	function ctrlr($state, spinnerService, BaAlertHttpService, $uibModal) {
		var vm = this;
		
		var paginationCtrlr = new PaginationCtrlr();
		
		// public variables
		vm.titlebarText = 'Alerts';
		vm.objs = [];
		vm.objsCurrentPage = 0;
		vm.objsPageNumbers = [];
		vm.objsTotalPages = 0;
		
		// public functions
		vm.addAlert = addAlert;
		vm.setObjsPage = setObjsPage;
		vm.titlebarBack = back;
		vm.alertUnsubscribe = alertUnsubscribe;
		
		init();
		
		function init() {
			setObjsPage(1);
		}
		
		function addAlert() {
			$state.go('app.gu.ba.add', {}, {location : false});
		}
		
		function setObjsPage(pageNumber) {
			var pageSize = 40;
			
			if (pageNumber < 1) {
				return;
			}
			
			paginationCtrlr.update(vm.objs.length, pageNumber, pageSize);
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			BaAlertHttpService.getMyAlertListPage(paginationCtrlr.startPage, pageSize).then(function(response) {
				if (!angular.isUndefined(response)) {
					var page = response.data.result;
					vm.objs = page.records;
					paginationCtrlr.update(page.total, pageNumber, pageSize);
					
					vm.objsCurrentPage = paginationCtrlr.currentPage;
					vm.objsPageNumbers = paginationCtrlr.pages;
					vm.objsTotalPages = paginationCtrlr.totalPages;
				}
				spinnerService.hide(appConsts.screenLoadingSpinner);
			});
		}
		
		function alertUnsubscribe(alert) {
			confirmUnsubscribe(alert.id);
		}
		
		function confirmUnsubscribe(alertId) {
			var modal = $uibModal.open({
				templateUrl : 'client/src/app/widget/modal/yes-no.modal.tpl.html',
				controller : 'YesNoModalCtrlr',
				controllerAs : "ctrlr",
				size : 'sm',
				resolve : {
					title : function() {
						return "Confirm Unsubscribe";
					},
					message : function() {
						return "Are you sure you want to unsubscribe from this alert?";
					},
					yesButtonText : function() {
						return "Ok";
					},
					noButtonText : function() {
						return "Cancel";
					}
				}
			});
			
			modal.result.then(function() {
				spinnerService.show(appConsts.screenLoadingSpinner);
				BaAlertHttpService.alertUnsubscribe(alertId).then(function() {
					setObjsPage(1);
				})
			});
		}
		
		function back() {
			$state.go('app.gu.settings', {}, {location : false});
		}
	};
});