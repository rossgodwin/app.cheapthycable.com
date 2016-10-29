define([
	'app/gu/bill/explorer/BillExplorerModule',
	'app/gu/dashboard/bill/BillExplorerCtrlr'
], function(
	BillExplorerModule,
	BillExplorerCtrlr
) {
	var module = angular.module('app.gu.dashboard.bill.explorer', ['app.gu.bill.explorer']);
	
	module.controller('DashboardBillExplorerCtrlr', BillExplorerCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.bill.explorer', {
			url : '/explorer',
			templateUrl : 'client/src/app/gu/dashboard/bill/bill-explorer.tpl.html',
			controller : 'DashboardBillExplorerCtrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize,
				startBill : ['$q', 'authorize', 'BillHttpService', function($q, authorize, BillHttpService) {
					var defer = $q.defer();
					BillHttpService.getLatestBill().then(function(result) {
						defer.resolve(result.data.result);
					});
					return defer.promise;
				}]
			}
		})
	}]);
	
	return module;
});