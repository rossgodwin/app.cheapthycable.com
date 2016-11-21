define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$state', 'BillHttpService', 'logoutSrvc', ctrlr];
	
	function ctrlr($window, $state, BillHttpService, logoutSrvc) {
		var vm = this;
		
		var billMgtState = 'app.gu.bill.mgt';
		var billExplorerState = 'app.gu.bill.explorer';
		
		// public variables
		vm.navbarAppName = $window.appName;
		vm.navbarLogoUrl = appUris.getLogoUrl();
		
		// public functions
		vm.stateGo = stateGo;
		vm.onMyBillsSelect = onMyBillsSelect;
		vm.onBillExplorerSelect = onBillExplorerSelect;
		vm.onSettingsSelect = onSettingsSelect;
		vm.redirectToFb = redirectToFb;
		vm.redirectToTwitter = redirectToTwitter;
		vm.logout = logout;
		
		init();
		
		function init() {
		}
		
		function stateGo(route) {
			$state.go(route, {}, {location : false});
		}
		
		function onMyBillsSelect() {
			stateGo(billMgtState);
		}
		
		function onBillExplorerSelect() {
			BillHttpService.getLatestBill().then(function(response) {
				if (response.data.resultCode === 1) {
					var latestBill = response.data.result;
					
					if (angular.isUndefined(latestBill)) {
						stateGo('app.gu.bill.create.node0');
					} else {
						stateGo(billExplorerState);
					}
				}
			});
		}
		
		function onSettingsSelect() {
			stateGo('app.gu.settings');
		}
		
		function redirectToFb() {
			$window.open($window.fbHomePageUrl, '_blank');
		}
		
		function redirectToTwitter() {
			$window.open($window.twitterHomePageUrl, '_blank');
		}
		
		function logout() {
			logoutSrvc.logout();
		}
	};
});