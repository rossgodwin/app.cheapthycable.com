define(['app/res/AppUris'], function(uris) {
	return ['$window', 'introBillCreateBaseState', 'billCreateNavHlpr', ctrlr];
	
	function ctrlr($window, introBillCreateBaseState, navHlpr) {
		var vm = this;
		
		// public variables
		vm.navbarAppName = $window.appName;
		vm.navbarLogoUrl = uris.getLogoUrl();
		
		init();
		
		function init() {
			navHlpr.setBaseState(introBillCreateBaseState);
			navHlpr.setOnFinishState('app.gu.bill.explorer');
		}
	}
});