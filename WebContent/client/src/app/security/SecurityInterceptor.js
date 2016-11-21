define(['src/app/res/AppUris'
], function(appUris) {
	return ['$q', '$window', intrcptr];
	
	function intrcptr($q, $window) {
		return {
			responseError : function(response) {
				if (response && response.status === 401) {
					$window.location.href = appUris.getLoginUrl();
				}
				$q.reject(response);
			}
		}
	}
});