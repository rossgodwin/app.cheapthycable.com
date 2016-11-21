define([
	'src/app/security/auth/AuthService',
	'src/app/admin/provider/ProviderMgtCtrlr'
], function(
	AuthService,
	ProviderMgtCtrlr) {
	var module = angular.module('app.admin.provider', []);
	
	module.factory('AuthService', AuthService);
	module.controller('ProviderMgtCtrlr', ProviderMgtCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['ADMIN']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.admin.provider', {
			url : '/provider',
			abstract : true,
			template: '<ui-view/>'
		})
		.state('app.admin.provider.mgt', {
			url : '/manage',
			templateUrl : 'client/src/app/admin/provider/provider-mgt.tpl.html',
			controller : 'ProviderMgtCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
	}]);
	
	return module;
});