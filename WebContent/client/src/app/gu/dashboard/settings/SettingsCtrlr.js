define([], function() {
	return ['$window', '$state', ctrlr];
	
	function ctrlr($window, $state) {
		var vm = this;
		
		// public variables
		vm.titlebarText = 'Settings';
		
		// public functions
		vm.titlebarBack = back;
		vm.onBaAlertSelect = onBaAlertSelect;
		
		init();
		
		function init() {
		}
		
		function stateGo(route) {
			$state.go(route, {}, {location : false});
		}
		
		function back() {
			stateGo('app.gu.dashboard');
		}
		
		function onBaAlertSelect() {
			stateGo('app.gu.ba.mgt');
		}
	};
});