define(['app/utils/BillUtils'], function(BillUtils) {
	return ['$stateParams', 'billCreateNavHlpr', 'billCreateModel', ctrlr];
	
	function ctrlr($stateParams, navHlpr, model) {
		var vm = this;
		
		// public variables
		vm.review = $stateParams.review;
		vm.bill = model;
		vm.internetOptions = angular.copy(vm.bill.internetOptions);
		vm.cableOptions = angular.copy(vm.bill.cableOptions);
		vm.downloadSpeedMbpsOptions = [1, 3, 5, 10, 25, 30, 50];
		vm.downloadSpeedMbpsRngOptions = [{lower: 1, upper: 5}, {lower: 6, upper: 10}, {lower: 11, upper: 25}, {lower: 26, upper: 50}, {lower: 51, upper: 300}, {lower: 301, upper: 1000}];
		vm.boxCountOptions = [0, 1, 2, 3, 4, 5];
		vm.dvrCountOptions = [0, 1, 2, 3, 4, 5];
		
		// public functions
		vm.renderDownloadSpeedRng = renderDownloadSpeedRng;
		vm.updDownloadSpeedRng = updDownloadSpeedRng;
		vm.prev = prev;
		vm.next = next;
		vm.finish = finish;
		
		function renderDownloadSpeedRng(lower, upper) {
			return BillUtils.renderInternetDownloadSpeedMbpsRng(lower, upper, false);
		}
		
		function updDownloadSpeedRng(optn) {
			vm.internetOptions.downloadSpeedMbpsRngLower = optn.lower;
			vm.internetOptions.downloadSpeedMbpsRngUpper = optn.upper;
		}
		
		function updateModel() {
			vm.bill.internetOptions = angular.copy(vm.internetOptions);
			vm.bill.cableOptions = angular.copy(vm.cableOptions);
		}
		
		function prev() {
			navHlpr.goToPrevState(navHlpr.getBaseState() + '.create.node2');
		}
		
		function next() {
			updateModel();
			navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node4');
		}
		
		function finish() {
			updateModel();
			$stateParams.review = false;
			navHlpr.goToState(navHlpr.getBaseState() + '.create.node5');
		}
	};
});