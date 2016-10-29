define([
	'common/ValuesEqualRqrdDrctv',
	'app/public/signup/SignupUniqueEmailChkDrctv',
	'app/widget/PwdValidChkDrctv',
	'app/public/signup/SignupModel',
	'app/public/MainCtrlr',
	'app/public/login/LoginCtrlr',
	'app/public/signup/SignupCtrlr',
	'app/public/signup/SignupVerifyCtrlr',
	'app/public/signup/SignupVerifiedCtrlr',
	'app/public/forgot/PwdForgotCtrlr',
	'app/public/forgot/PwdForgotSuccessCtrlr',
	'app/public/reset/PwdResetCtrlr',
	'app/public/reset/PwdResetSuccessCtrlr'
], function(
	ValuesEqualRqrdDrctv,
	SignupUniqueEmailChkDrctv,
	PwdValidChkDrctv,
	SignupModel,
	MainCtrlr,
	LoginCtrlr,
	SignupCtrlr,
	SignupVerifyCtrlr,
	SignupVerifiedCtrlr,
	PwdForgotCtrlr,
	PwdForgotSuccessCtrlr,
	PwdResetCtrlr,
	PwdResetSuccessCtrlr) {
	var module = angular.module('app.login', ['ngMessages']);
	
	module.directive('valuesEqualRqrd', ValuesEqualRqrdDrctv);
	module.directive('signupUniqueEmailChk', SignupUniqueEmailChkDrctv);
	module.directive('pwdValidChk', PwdValidChkDrctv);
	
	module.factory('SignupModel', SignupModel);
	
	module.controller('PublicMainCtrlr', MainCtrlr);
	module.controller('LoginCtrlr', LoginCtrlr);
	module.controller('SignupCtrlr', SignupCtrlr);
	module.controller('SignupVerifyCtrlr', SignupVerifyCtrlr);
	module.controller('SignupVerifiedCtrlr', SignupVerifiedCtrlr);
	module.controller('PwdForgotCtrlr', PwdForgotCtrlr);
	module.controller('PwdForgotSuccessCtrlr', PwdForgotSuccessCtrlr);
	module.controller('PwdResetCtrlr', PwdResetCtrlr);
	module.controller('PwdResetSuccessCtrlr', PwdResetSuccessCtrlr);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app', {
			abstract : true,
			templateUrl : 'client/src/app/public/app-public.tpl.html',
			controller : 'PublicMainCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.login', {
			url : '/login',
			templateUrl : 'client/src/app/public/login/login.tpl.html',
			controller : 'LoginCtrlr',
			controllerAs : 'ctrlr'
		})
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
		.state('app.forgot', {
			url : '/forgot',
			templateUrl : 'client/src/app/public/forgot/pwd-forgot.tpl.html',
			controller : 'PwdForgotCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.forgot-success', {
			params : {
				email : ''
			},
			templateUrl : 'client/src/app/public/forgot/pwd-forgot-success.tpl.html',
			controller : 'PwdForgotSuccessCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.reset', {
			url : '/reset-password?token',
			templateUrl : 'client/src/app/public/reset/pwd-reset.tpl.html',
			controller : 'PwdResetCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.reset-success', {
			url : '/reset-password-success',
			templateUrl : 'client/src/app/public/reset/pwd-reset-success.tpl.html',
			controller : 'PwdResetSuccessCtrlr',
			controllerAs : 'ctrlr'
		});
	}]);
	
	return module;
});