define(['app/res/AppUris'], function(appUris) {
	return ['$window', '$state', 'logoutSrvc', ctrlr];
	
	function ctrlr($window, $state, logoutSrvc) {
		var vm = this;
		
		var providerMgtState = 'app.admin.provider.mgt';
		
		// public variables
		vm.navbarAppName = $window.appName;
		vm.navbarLogoUrl = appUris.getLogoUrl();
		
		// public functions
		vm.stateGo = stateGo;
		vm.onProvidersSelect = onProvidersSelect;
		vm.logout = logout;
		
		init();
		
		function init() {
		}
		
		function stateGo(route) {
			$state.go(route, {}, {location : false});
		}
		
		function onProvidersSelect() {
			stateGo(providerMgtState);
		}
		
		function logout() {
			logoutSrvc.logout();
		}
	};
});