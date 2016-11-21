define([
	'src/app/gu/dashboard/settings/SettingsCtrlr',
	'src/app/gu/dashboard/settings/ba/BaAlertModule'
], function(
	SettingsCtrlr,
	BaAlertModule) {
	var module = angular.module('app.gu.dashboard.settings', ['app.gu.dashboard.ba']);
	
	module.controller('SettingsCtrlr', SettingsCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.settings', {
			url : '/settings',
			templateUrl : 'client/src/app/gu/dashboard/settings/settings.tpl.html',
			controller : 'SettingsCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
	}]);
	
	return module;
});