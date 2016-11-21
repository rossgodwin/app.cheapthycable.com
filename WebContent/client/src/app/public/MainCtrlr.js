define(['src/app/res/AppUris'], function(appUris) {
	var ctrlr = function($window) {
		var vm = this;
		
		vm.goToHome = goToHome;
		vm.appName = $window.appName;
		vm.logoUrl = appUris.getLogoUrl();
		
		function goToHome() {
			$window.location.href = appUris.getHomeUrl();
		}
	};
	
	return ['$window', ctrlr];
});