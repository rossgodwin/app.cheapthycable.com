define([
	'app/res/AppUris',
	'app/gu/AppSecureTemplates',
	'app/core/CoreModule',
	'app/security/SecurityInterceptor',
	'app/gu/MainModule',
	'app/admin/MainModule',
	'app/social/SocialModule'
], function(
	appUris,
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
	
	module.run(['$location', '$templateCache', '$compile', '$rootScope', '$state', 'AuthService', 'principal', 'BillHttpService', function($location, $templateCache, $compile, $rootScope, $state, AuthService, principal, BillHttpService) {
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
						
						if (angular.isUndefined(latestBill)) {
							$state.go('app.gu.intro.welcome', {}, {location : false});
						} else {
							var url = $location.path();
							if (url == appUris.getAppUrl()) {
								$state.go('app.gu.dashboard', {}, {location : false});
							} else {
								$location.path($location.path());
							}
						}
						
						removeSplash();
					}
				});
			}
		})
	}])
	
	setTimeout(asyncBootstrap, 1);
	
	function asyncBootstrap() {
		angular.bootstrap(document.getElementsByTagName("body")[0], [moduleName]);
	}
	
	function removeSplash() {
		/**
		 * The native childNode.remove() is a new experimental method that is not is supported in Internet Explorer, only in Edge
		 * http://stackoverflow.com/questions/20428877/javascript-remove-doesnt-work-in-ie
		 */
		if (!('remove' in Element.prototype)) {
		    Element.prototype.remove = function() {
		        if (this.parentNode) {
		            this.parentNode.removeChild(this);
		        }
		    };
		}
		
		var element = document.getElementById('app-splash');
		element.remove();
		element = null;
	}
	
	return module;
});