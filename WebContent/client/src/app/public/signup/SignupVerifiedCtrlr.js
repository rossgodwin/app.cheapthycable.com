define([], function() {
	return ['$state', ctrlr];
	
	function ctrlr($state) {
		var vm = this;
		
		// public functions
		vm.goToLogin = goToLogin;
		
		function goToLogin() {
			$state.go('app.login');
		};
	}
})