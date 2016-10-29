define([], function() {
	return ['$stateParams', 'billCreateNavHlpr', ctrlr];
	
	function ctrlr($stateParams, navHlpr) {
		var vm = this;
		
		// public variables
		vm.myBill = $stateParams.myBill;
		
		// public functions
		vm.next = next;
		
		init();
		
		function init() {
		}
		
		function next() {
			navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node7');
		}
	};
});