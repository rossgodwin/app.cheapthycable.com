define([], function() {
	return ['startBill', '$state', ctrlr];
	
	function ctrlr(startBill, $state) {
		var vm = this;
		
		// public variables
		vm.titlebarText = 'Bill Explorer';
		vm.startBill = startBill;
		
		// public functions
		vm.titlebarBack = back;
		
		function back() {
			$state.go('app.gu.dashboard', {}, {location : false});
		}
	}
});