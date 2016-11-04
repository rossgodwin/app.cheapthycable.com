define([
	'app/gu/AppCtrlr',
	'app/gu/intro/IntroModule',
	'app/gu/dashboard/DashboardModule'
], function(
	AppCtrlr,
	IntroModule,
	DashboardModule) {
	var module = angular.module('app.gu', ['ui.bootstrap', 'ngMessages', 'app.gu.intro', 'app.gu.dashboard']);

	module.controller('GuAppCtrlr', AppCtrlr);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu', {
			templateUrl : 'client/src/app/gu/app.tpl.html',
			controller : 'GuAppCtrlr',
			controllerAs : 'ctrlr'
		})
	}]);
	
	return module;
});