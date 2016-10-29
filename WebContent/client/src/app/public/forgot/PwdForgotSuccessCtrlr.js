define([], function() {
	return ['$state', '$stateParams', ctrlr];
	
	function ctrlr($state, $stateParams) {
		var vm = this;
		
		// public variables
		vm.email = $stateParams.email;
		
		// public functions
		vm.goToForgot = goToForgot;
		
		function goToForgot() {
			$state.go('app.forgot');
		};
	}
});