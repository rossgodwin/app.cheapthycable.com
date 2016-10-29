define([], function() {
	return ['$stateParams', 'billCreateNavHlpr', 'billCreateModel', ctrlr];
	
	function ctrlr($stateParams, navHlpr, model) {
		var vm = this;
		
		// public variables
		vm.review = $stateParams.review;
		vm.bill = model;
		vm.comments = vm.bill.comments;
		
		// public functions
		vm.prev = prev;
		vm.next = next;
		vm.finish = finish;
		
		function updateModel() {
			vm.bill.comments = vm.comments;
		}
		
		function prev() {
			navHlpr.goToPrevState(navHlpr.getBaseState() + '.create.node3');
		}
		
		function next() {
			updateModel();
			navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node5');
		}
		
		function finish() {
			updateModel();
			$stateParams.review = false;
			navHlpr.goToState(navHlpr.getBaseState() + '.create.node5');
		}
	};
});