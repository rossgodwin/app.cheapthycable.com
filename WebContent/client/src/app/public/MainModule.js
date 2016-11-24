define([
	'src/common/ValuesEqualRqrdDrctv',
	'src/app/widget/PwdValidChkDrctv',
	'src/app/public/MainCtrlr',
	'src/app/public/login/LoginCtrlr',
	'src/app/public/forgot/PwdForgotCtrlr',
	'src/app/public/forgot/PwdForgotSuccessCtrlr',
	'src/app/public/reset/PwdResetCtrlr',
	'src/app/public/reset/PwdResetSuccessCtrlr',
	'src/app/public/ba/BaAlertUnsubscribeSuccessCtrlr',
	'src/app/public/qsignup/SignupModule'
], function(
	ValuesEqualRqrdDrctv,
	PwdValidChkDrctv,
	MainCtrlr,
	LoginCtrlr,
	PwdForgotCtrlr,
	PwdForgotSuccessCtrlr,
	PwdResetCtrlr,
	PwdResetSuccessCtrlr,
	BaAlertUnsubscribeSuccessCtrlr,
	SignupModule) {
	var module = angular.module('app.login', ['ngMessages', 'app.signup']);
	
	module.directive('valuesEqualRqrd', ValuesEqualRqrdDrctv);
	module.directive('pwdValidChk', PwdValidChkDrctv);
	
	module.controller('PublicMainCtrlr', MainCtrlr);
	module.controller('LoginCtrlr', LoginCtrlr);
	module.controller('PwdForgotCtrlr', PwdForgotCtrlr);
	module.controller('PwdForgotSuccessCtrlr', PwdForgotSuccessCtrlr);
	module.controller('PwdResetCtrlr', PwdResetCtrlr);
	module.controller('PwdResetSuccessCtrlr', PwdResetSuccessCtrlr);
	module.controller('BaAlertUnsubscribeSuccessCtrlr', BaAlertUnsubscribeSuccessCtrlr);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app', {
			abstract : true,
			templateUrl : 'client/src/app/public/app-public.tpl.html',
			controller : 'PublicMainCtrlr',
			controllerAs : 'ctrlr'
		})
		.state('app.login', {
			url : '/login?go',
			templateUrl : 'client/src/app/public/login/login.tpl.html',
			controller : 'LoginCtrlr',
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
		})
		.state('app.ba-unsubscribe-success', {
			url : '/alert-unsubscribe-success',
			templateUrl : 'client/src/app/public/ba/ba-alert-unsubscribe-success.tpl.html',
			controller : 'BaAlertUnsubscribeSuccessCtrlr',
			controllerAs : 'ctrlr'
		});
	}]);
	
	return module;
});