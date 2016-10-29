define([], function() {
	return ['dashboardBillCreateBaseState', 'billCreateNavHlpr', '$state', '$uibModal', ctrlr];
	
	function ctrlr(dashboardBillCreateBaseState, navHlpr, $state, $uibModal) {
		var vm = this;
		
		// public variables
		vm.titlebarText = 'New Bill';
		
		// public functions
		vm.titlebarBack = back;
		
		init();
		
		function init() {
			navHlpr.setBaseState(dashboardBillCreateBaseState);
			navHlpr.setOnFinishState('app.gu.bill.explorer');
		}
		
		function back() {
			var modal = $uibModal.open({
				templateUrl : 'client/src/app/widget/modal/yes-no.modal.tpl.html',
				controller : 'YesNoModalCtrlr',
				controllerAs : "ctrlr",
				size : 'sm',
				resolve : {
					title : function() {
						return "Confirm Cancel";
					},
					message : function() {
						return "Are you sure you want to cancel? You will lose all your entries.";
					},
					yesButtonText : function() {
						return "Yes";
					},
					noButtonText : function() {
						return "No";
					}
				}
			});
			
			modal.result.then(function() {
				$state.go(navHlpr.getBaseState() + '.mgt', {}, {location : false});
			});
		}
	}
});