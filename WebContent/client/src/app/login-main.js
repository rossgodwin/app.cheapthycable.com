define([
	'app/public/AppPublicTemplates',
	'app/core/CoreModule',
	'app/public/MainModule'
], function(
	Templates,
	CoreModule,
	MainModule) {
	var moduleName = 'app';
	
	var module = angular.module(moduleName, ['app.public.templates', 'app.core', 'app.login']);
	
	module.run(['$templateCache', '$compile', '$rootScope', '$location', '$state', function($templateCache, $compile, $rootScope, $location, $state) {
		var templatesHTML = $templateCache.get('app.public.templates');
		$compile(templatesHTML)($rootScope);
	}])
	
	angular.bootstrap(document.getElementsByTagName("body")[0], [moduleName]);
	
	return module;
});