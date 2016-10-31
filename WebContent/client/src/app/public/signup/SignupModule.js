define([
	'app/public/signup/SignupUniqueEmailChkDrctv',
	'app/public/signup/SignupModel',
	'app/public/signup/SignupCtrlr',
	'app/public/signup/SignupVerifyCtrlr',
	'app/public/signup/SignupVerifiedCtrlr'
], function(
	SignupUniqueEmailChkDrctv,
	SignupModel,
	SignupCtrlr,
	SignupVerifyCtrlr,
	SignupVerifiedCtrlr
) {
	var module = angular.module('app.signup', ['ngMessages']);
	
	module.directive('signupUniqueEmailChk', SignupUniqueEmailChkDrctv);
	
	module.factory('SignupModel', SignupModel);
	
	module.controller('SignupCtrlr', SignupCtrlr);
	module.controller('SignupVerifyCtrlr', SignupVerifyCtrlr);
	module.controller('SignupVerifiedCtrlr', SignupVerifiedCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.signup', {
			url : '/signup',
			templateUrl : 'client/src/app/public/signup/signup.tpl.html',
			controller : 'SignupCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.signup-verify', {
			params : {
				email : ''
			},
			templateUrl : 'client/src/app/public/signup/signup-verify.tpl.html',
			controller : 'SignupVerifyCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.signup-verified', {
			url : '/signup-verified',
			templateUrl : 'client/src/app/public/signup/signup-verified.tpl.html',
			controller : 'SignupVerifiedCtrlr',
			controllerAs : 'ctrlr'
		})
	}]);
	
	return module;
});