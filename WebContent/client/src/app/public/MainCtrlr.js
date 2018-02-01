define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', ctrlr];
	
	function ctrlr($window) {
		var vm = this;
		
		vm.goToHome = goToHome;
		vm.appName = $window.appName;
		vm.logoUrl = appUris.getLogoUrl();
		
		function goToHome() {
			$window.location.href = appUris.getHomeUrl();
		}
	};
});