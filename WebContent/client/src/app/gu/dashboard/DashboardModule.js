define([
	'src/app/gu/dashboard/DashboardCtrlr',
	'src/app/gu/dashboard/bill/BillModule',
	'src/app/gu/dashboard/settings/SettingsModule'
], function(
	DashboardCtrlr,
	BillModule,
	SettingsModule) {
	var module = angular.module('app.gu.dashboard', ['app.gu.dashboard.bill', 'app.gu.dashboard.settings']);
	
	module.controller('GuDashboardCtrlr', DashboardCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.dashboard', {
			url : '/dashboard',
			templateUrl : 'client/src/app/gu/dashboard/dashboard.tpl.html',
			controller : 'GuDashboardCtrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize
			}
		})
	}]);
	
	return module;
});