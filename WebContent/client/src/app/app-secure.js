define([
	'app/gu/AppSecureTemplates',
	'app/core/CoreModule',
	'app/security/SecurityInterceptor',
	'app/gu/MainModule',
	'app/admin/MainModule',
	'app/social/SocialModule'
], function(
	Templates,
	CoreModule,
	SecurityInterceptor,
	UserMainModule,
	AdminMainModule,
	SocialModule) {
	var moduleName = 'app';

	var module = angular.module(moduleName, ['app.templates', 'app.core', 'app.social', 'app.gu', 'app.admin']);
	
	module.factory('SecurityInterceptor', SecurityInterceptor);
	
//	module.factory('$exceptionHandler', function($log, spinnerService) {
//		return function(err, cause) {
//			// TODO perhaps redirect to error state here
//			spinnerService.hideAll();
//			$log.error.apply($log, arguments);
//		};
//	});
	
	module.config(['$httpProvider', function($httpProvider) {
		$httpProvider.interceptors.push('SecurityInterceptor');
	}]);
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app', {
			url : '/app',
			abstract : true,
			templateUrl : "client/src/app/app.tpl.html"
		})
		.state('app.accessDenied', {
			url : "/accessDenied",
			template : '<div style="text-align: center;"><h2>Access Denied</h2></div>'
		})
	}]);
	
	module.run(['$templateCache', '$compile', '$rootScope', '$state', 'AuthService', 'principal', 'BillHttpService', function($templateCache, $compile, $rootScope, $state, AuthService, principal, BillHttpService) {
		var templatesHTML = $templateCache.get('app.templates');
		$compile(templatesHTML)($rootScope);
		
		AuthService.authorize(['ADMIN','USER']).then(function() {
			if (principal.isInRole('ADMIN')) {
				$state.go('app.admin.dashboard', {}, {location: false});
				
				removeSplash();
			} else {
				BillHttpService.getLatestBill().then(function(response) {
					if (response.data.resultCode === 1) {
						var latestBill = response.data.result;
						
						var startState;
						if (angular.isUndefined(latestBill)) {
							startState = 'app.gu.intro.welcome';
						} else {
							startState = 'app.gu.dashboard';
						}
						
						$state.go(startState, {}, {location : false});
						
						removeSplash();
					}
				});
			}
		})
	}])
	
//	angular.bootstrap(document.getElementsByTagName("body")[0], [moduleName]);
	
//	setTimeout(function asyncBootstrap() {
//		angular.bootstrap(document.getElementsByTagName("body")[0], [moduleName]);
//
//			},
//			( 2 * 1000 )
//		);
	
//	setTimeout(asyncBootstrap, (2 * 1000));
	setTimeout(asyncBootstrap, 1);
//	asyncBootstrap2();
	
	function asyncBootstrap() {
		angular.bootstrap(document.getElementsByTagName("body")[0], [moduleName]);
	}
	
	function removeSplash() {
		var element = document.getElementById('app-splash');
		element.remove();
		element = null;
	}
	
	return module;
});