define([
	'src/app/res/AppUris'
], function(appUris) {
	return ['$window', '$state', 'principal', srvc];
	
	function srvc($window, $state, principal) {
		return {
			authorize : function(roles) {
				return principal.identity(true).then(function() {
					var isAuthenticated = principal.isAuthenticated();
					
					if (roles && roles.length > 0 && !principal.isInAnyRole(roles)) {
						if (isAuthenticated) {
							$state.go('app.accessDenied');
						} else {
							var requestedUrl = $window.location.href;
							$window.location.href = appUris.getLoginUrl() + '?go=' + requestedUrl;
						}
					}
				});
			}
		};
	};
})