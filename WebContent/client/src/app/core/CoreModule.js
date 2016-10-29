define([
	'common/pagination/PaginationDrctv',
	'app/core/AppNavbar0Drctv',
	'app/core/AppTitlebar0Drctv',
	'app/core/AppIconTextBtn0Drctv',
	'app/security/auth/AuthService',
	'app/security/auth/Principal',
	'app/net/NetModule',
	'app/core/LocationSrvc',
	'app/core/LogoutSrvc',
	'app/widget/modal/YesNoModalCtrlr'
], function(
	PaginationDrctv,
	AppNavbar0Drctv,
	AppTitlebar0Drctv,
	AppIconTextBtn0Drctv,
	AuthService,
	Principal,
	NetModule,
	LocationSrvc,
	LogoutSrvc,
	YesNoModalCtrlr) {
	var module = angular.module('app.core', ['ui.router', 'angularSpinners', 'ngSanitize', 'app.net']);
	
	module.config(['$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode(true);
	}]);
	
	module.directive('appPagination', PaginationDrctv);
	module.directive('appNavbar0', AppNavbar0Drctv);
	module.directive('appTitlebar0', AppTitlebar0Drctv);
	module.directive('appIconTextBtn0', AppIconTextBtn0Drctv);
	
	module.factory('principal', Principal);
	module.factory('AuthService', AuthService);
	
	module.service('locationSrvc', LocationSrvc);
	module.service('logoutSrvc', LogoutSrvc);
	
	module.controller('YesNoModalCtrlr', YesNoModalCtrlr);
	
	return module;
});