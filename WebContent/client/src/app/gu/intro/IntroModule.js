define([
	'app/gu/intro/BillCreateModule',
	'app/gu/intro/WelcomeCtrlr'
], function(
	BillCreateModule,
	WelcomeCtrlr
) {
	var module = angular.module('app.gu.intro', ['app.gu.intro.bill.create']);

	module.controller('WelcomeCtrlr', WelcomeCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.intro', {
			abstract : true,
			templateUrl : 'client/src/app/gu/intro/intro.tpl.html'
		})
		.state('app.gu.intro.welcome', {
			url : '/welcome',
			templateUrl : 'client/src/app/gu/intro/welcome.tpl.html',
			controller : 'WelcomeCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
	}]);
	
	return module;
});