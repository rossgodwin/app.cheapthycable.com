define([], function() {
	return ['$stateParams', 'billCreateNavHlpr', 'billCreateModel', ctrlr];
	
	function ctrlr($stateParams, navHlpr, model) {
		var vm = this;
		
		// public variables
		vm.visited = false;
		vm.review = $stateParams.review;
		vm.bill = model;
		vm.internetService = false;
		vm.cableService = false;
		vm.phoneService = false;
		vm.errs = [];
		
		// public functions
		vm.prev = prev;
		vm.next = next;
		vm.finish = finish;
		
		init();
		
		function init() {
			if (vm.bill.getServices().length > 0 || vm.review) {
				vm.internetService = vm.bill.internetService;
				vm.cableService = vm.bill.cableService;
				vm.phoneService = vm.bill.phoneService;
				vm.visited = true;
			}
		}
		
		function validate() {
			vm.errs = [];
			if (!vm.internetService && !vm.cableService && !vm.phoneService) {
				vm.errs.push('You gotta be paying for something.  Please select 1 or more services.');
			}
		}
		
		function updateModel() {
			vm.bill.internetService = vm.internetService;
			vm.bill.cableService = vm.cableService;
			vm.bill.phoneService = vm.phoneService;
		}
		
		function prev() {
			navHlpr.goToPrevState(navHlpr.getBaseState() + '.create.node1');
		}
		
		function next() {
			validate();
			if (vm.errs.length == 0) {
				updateModel();
				if (vm.internetService || vm.cableService) {
					navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node3');
				} else {
					navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node4');
				}
			}
		}
		
		function finish() {
			validate();
			if (vm.errs.length == 0) {
				updateModel();
				$stateParams.review = false;
				navHlpr.goToState(navHlpr.getBaseState() + '.create.node5');
			}
		}
	};
});