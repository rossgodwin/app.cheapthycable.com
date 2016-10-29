define([
	'app/admin/AppCtrlr',
	'app/admin/dashboard/DashboardModule'
], function(
	AppCtrlr,
	DashboardModule) {
	var module = angular.module('app.admin', ['ui.bootstrap', 'app.admin.dashboard']);
	
	module.controller('AdminAppCtrlr', AppCtrlr);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.admin', {
			templateUrl : 'client/src/app/admin/app.tpl.html',
			controller : 'AdminAppCtrlr',
			controllerAs : 'ctrlr'
		})
	}]);
	
	return module;
});