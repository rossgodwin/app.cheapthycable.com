define([
	'app/admin/dashboard/DashboardCtrlr',
	'app/admin/provider/ProviderModule'
], function(
	DashboardCtrlr,
	ProviderModule) {
	var module = angular.module('app.admin.dashboard', ['app.admin.provider']);
	
	module.controller('AdminDashboardCtrlr', DashboardCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['ADMIN']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.admin.dashboard', {
			templateUrl : 'client/src/app/admin/dashboard/dashboard.tpl.html',
			controller : 'AdminDashboardCtrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize
			}
		})
	}]);
	
	return module;
});