define([
	'src/app/social/FbHlprPrvdr',
	'src/app/social/TwitterHlprPrvdr'
], function(
	FbHlprPrvdr,
	TwitterHlprPrvdr) {
	var module = angular.module('app.social', ['ezfb']);
	
	module.config(['$windowProvider', 'ezfbProvider', function($windowProvider, ezfbProvider) {
		var $window = $windowProvider.$get();
		
		ezfbProvider.setInitParams({
			appId : $window.fbAppId,
			version: 'v2.8'
		});
	}]);
	
	module.service('fbHlprPrvdr', FbHlprPrvdr);
	module.service('twitterHlprPrvdr', TwitterHlprPrvdr);
	
	return module;
});