define([
	'app/res/AppUris'
], function(uris) {
	var service = function($http) {
		var service = {
			getProviderList : getProviderList,
			getProviderListPage : getProviderListPage
		};
		
		return service;
		
		function getProviderList(searchCritr) {
			return $http.get(uris.getRestUrl('/provider/list'), {
				params : {
					p0 : searchCritr
				}
			});
		}
		
		function getProviderListPage(searchCritr, offset, limit) {
			return $http.get(uris.getRestUrl('/provider/list/page'), {
				params : {
					p0 : searchCritr,
					p1 : offset,
					p2 : limit
				}
			});
		}
	};
	
	return ['$http', service];
});