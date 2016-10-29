define(['app/utils/BillUtils', 'app/res/AppConsts'], function(BillUtils, appConsts) {
	return ['$state', '$stateParams', 'spinnerService', 'billCreateNavHlpr', 'billCreateModel', 'BillHttpService', ctrlr];
	
	function ctrlr($state, $stateParams, spinnerService, navHlpr, model, BillHttpService) {
		var vm = this;
		
		// public variables
		vm.bill = model;
		
		// public functions
		vm.renderInternetOptionsDownloadSpeedMbps = renderInternetOptionsDownloadSpeedMbps;
		vm.goToNode0 = goToNode0;
		vm.goToNode1 = goToNode1;
		vm.goToNode2 = goToNode2;
		vm.goToNode3 = goToNode3;
		vm.goToNode4 = goToNode4;
		vm.save = save;
		
		function renderInternetOptionsDownloadSpeedMbps() {
			return BillUtils.renderInternetDownloadSpeedMbps(vm.bill.internetOptions, true);
		}
		
		function goToNode0() {
			$state.go(navHlpr.getBaseState() + '.create.node0', {review : true}, {location : false});
		}
		
		function goToNode1() {
			$state.go(navHlpr.getBaseState() + '.create.node1', {review : true}, {location : false});
		}
		
		function goToNode2() {
			$state.go(navHlpr.getBaseState() + '.create.node2', {review : true}, {location : false});
		}
		
		function goToNode3() {
			$state.go(navHlpr.getBaseState() + '.create.node3', {review : true}, {location : false});
		}
		
		function goToNode4() {
			$state.go(navHlpr.getBaseState() + '.create.node4', {review : true}, {location : false});
		}
		
		function save() {
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			BillHttpService.saveBill(vm.bill).then(function(response) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (response.data.resultCode === 1) {
					navHlpr.goToNextState2(navHlpr.getBaseState() + '.create.node6', {myBill : response.data.result});
				}
			}).catch(function(error) {
				// TODO
			});
		}
	};
});