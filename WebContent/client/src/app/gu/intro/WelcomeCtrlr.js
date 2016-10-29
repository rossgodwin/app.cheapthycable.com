define(['app/res/AppUris'], function(uris) {
	return ['$window', '$state', ctrlr];
	
	function ctrlr($window, $state) {
		var vm = this;
		
		// public variables
		vm.navbarAppName = $window.appName;
		vm.navbarLogoUrl = uris.getLogoUrl();
		
		// public functions
		vm.go = go; 
		
		function go() {
			$state.go('app.gu.intro.create.node0', {}, {location : false});
		}
	};
});