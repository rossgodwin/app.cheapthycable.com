define([
	'src/app/public/signup/SignupUniqueEmailChkDrctv',
	'src/app/public/signup/SignupModel',
	'src/app/public/qsignup/SignupCtrlr'
], function(
	SignupUniqueEmailChkDrctv,
	SignupModel,
	SignupCtrlr
) {
	var module = angular.module('app.signup', []);
	
	module.directive('signupUniqueEmailChk', SignupUniqueEmailChkDrctv);
	
	module.factory('SignupModel', SignupModel);
	
	module.controller('SignupCtrlr', SignupCtrlr);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.signup', {
			url : '/signup',
			templateUrl : 'client/src/app/public/qsignup/signup.tpl.html',
			controller : 'SignupCtrlr',
			controllerAs : 'ctrlr'
		})
	}]);
	
	return module;
});