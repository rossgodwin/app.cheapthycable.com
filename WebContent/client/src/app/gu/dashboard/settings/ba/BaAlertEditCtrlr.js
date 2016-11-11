define([], function() {
	return ['$state', '$stateParams', ctrlr];
	
	function ctrlr($state, $stateParams) {
		var vm = this;
		
		// public variables
		vm.titlebarText = $stateParams.titlebarText;
		vm.frequencyDayOptions = [7, 14, 21];
		vm.mileRadiusOptions = [25, 50, 75];
		vm.receiveEmail = true;
		vm.receiveEmailFrequencyDays = 14;
		vm.critrMileRadius = 50;
		vm.critrAmountBelow = 50.00;
		
		// public functions
		vm.titlebarBack = back;
		
		function back() {
			$state.go('app.gu.ba.mgt', {}, {location : false});
		}
	}
});