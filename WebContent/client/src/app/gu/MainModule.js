define([
	'src/assets/js/directives/dirDisqus',
	'src/app/gu/AppCtrlr',
	'src/app/gu/intro/IntroModule',
	'src/app/gu/dashboard/DashboardModule'
], function(
	dirDisqus,
	AppCtrlr,
	IntroModule,
	DashboardModule) {
	var module = angular.module('app.gu', ['ui.bootstrap', 'ngMessages', 'angularUtils.directives.dirDisqus', 'app.gu.intro', 'app.gu.dashboard']);

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