define(['app/res/AppConsts'], function(consts) {
	return ['newBillStateName', '$state', 'BillHttpService', 'spinnerService', '$uibModal', ctrlr];
	
	function ctrlr(newBillStateName, $state, BillHttpService, spinnerService, $uibModal) {
		var vm = this;
		
		// public variables
		vm.titlebarText = "My Bills";
		
		// public functions
		vm.titlebarBack = back;
		vm.deleteBill = deleteBill;
		vm.newBill = newBill;
		vm.confirmDelete = confirmDelete;
		
		init();
		
		function init() {
			spinnerService.show(consts.screenLoadingSpinner);
			loadBills();
		}
		
		function back() {
			$state.go('app.gu.dashboard', {}, {location : false});
		}
		
		function deleteBill(bill) {
			confirmDelete(bill.id);
		}
		
		function loadBills() {
			BillHttpService.getMyBillsList().then(function(bills) {
				vm.bills = bills;
				spinnerService.hide(consts.screenLoadingSpinner);
			})
		}
		
		function newBill() {
			$state.go(newBillStateName, {}, {location : false});
		}
		
		function confirmDelete(billId) {
			var modal = $uibModal.open({
				templateUrl : 'client/src/app/widget/modal/yes-no.modal.tpl.html',
				controller : 'YesNoModalCtrlr',
				controllerAs : "ctrlr",
				size : 'sm',
				resolve : {
					title : function() {
						return "Confirm Delete";
					},
					message : function() {
						return "Are you sure you want to delete this bill?";
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
				spinnerService.show(consts.screenLoadingSpinner);
				BillHttpService.deleteBill(billId).then(function() {
					loadBills();
				})
			});
		}
	};
});