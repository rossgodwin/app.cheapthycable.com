define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$http', srvc];
	
	function srvc($window, $http) {
		return {
			hasAlert : hasAlert,
			addDefaultAlert : addDefaultAlert,
			getMyAlertListPage : getMyAlertListPage,
			alertUnsubscribe : alertUnsubscribe,
			saveOrUpdateAlert : saveOrUpdateAlert
		};
		
		function hasAlert() {
			return $http.get(appUris.getRestUrl('/baAlert/hasAlert'), null).catch(errorCallback);
		}
		
		function addDefaultAlert(email) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/baAlert/addDefaultAlert'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	email : email
			    }
			}).catch(errorCallback);
		}
		
		function getMyAlertListPage(offset, limit) {
			return $http.get(appUris.getRestUrl('/baAlert/my/list/page'), {
				params : {
					p0 : offset,
					p1 : limit
				}
			}).catch(errorCallback);
		}
		
		function alertUnsubscribe(alertId) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/baAlert/' + alertId + '/unsubscribe'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    }
			}).catch(errorCallback);
		}
		
		function saveOrUpdateAlert(obj) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/baAlert/saveOrUpdate'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : JSON.stringify(obj)
			    }
			}).catch(errorCallback);
		}
		
		function errorCallback(response) {
			if (response.status == 401) {
				$window.location.href = appUris.getLoginUrl();
			}
		}
	}
});