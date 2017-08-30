define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$http', srvc];
	
	function srvc($window, $http) {
		var service = {
			getProviderList : getProviderList,
			getProviderListPage : getProviderListPage
		};
		
		return service;
		
		function getProviderList(searchCritr) {
			return $http.get(appUris.getRestUrl('/provider/list'), {
				params : {
					p0 : searchCritr
				}
			}).catch(errorCallback);
		}
		
		function getProviderListPage(searchCritr, offset, limit) {
			return $http.get(appUris.getRestUrl('/provider/list/page'), {
				params : {
					p0 : searchCritr,
					p1 : offset,
					p2 : limit
				}
			}).catch(errorCallback);
		}
		
		function errorCallback(response) {
			if (response.status == 401) {
				$window.location.href = appUris.getLoginUrl();
			}
		}
	};
});