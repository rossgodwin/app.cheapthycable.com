define([
	'app/gu/dashboard/settings/ba/BaAlertModelPrvdr',
	'app/gu/dashboard/settings/ba/BaAlertMgtCtrlr',
	'app/gu/dashboard/settings/ba/BaAlertEditCtrlr'
], function(
	BaAlertModelPrvdr,
	BaAlertMgtCtrlr,
	BaAlertEditCtrlr) {
	var module = angular.module('app.gu.dashboard.ba', []);
	
	module.provider('baAlertModel', BaAlertModelPrvdr);
	
	module.controller('BaAlertMgtCtrlr', BaAlertMgtCtrlr);
	module.controller('BaAlertEditCtrlr', BaAlertEditCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.ba', {
			url : '/alerts',
			abstract : true,
			template: '<ui-view/>'
		})
		.state('app.gu.ba.mgt', {
			url : '/manage',
			templateUrl : 'client/src/app/gu/dashboard/settings/ba/ba-alert-mgt.tpl.html',
			controller : 'BaAlertMgtCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state('app.gu.ba.add', {
			params : {
				titlebarText : 'Add Alert'
			},
			url : '/add',
			templateUrl : 'client/src/app/gu/dashboard/settings/ba/ba-alert-edit.tpl.html',
			controller : 'BaAlertEditCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
	}]);
	
	return module;
});