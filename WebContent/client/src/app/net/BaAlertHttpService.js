define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			hasAlert : hasAlert,
			addDefaultAlert : addDefaultAlert,
			getMyAlertListPage : getMyAlertListPage,
			alertUnsubscribe : alertUnsubscribe,
			saveOrUpdateAlert : saveOrUpdateAlert
		};
		
		function hasAlert() {
			return $http.get(appUris.getRestUrl('/baAlert/hasAlert'), null);
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
			});
		}
		
		function getMyAlertListPage(offset, limit) {
			return $http.get(appUris.getRestUrl('/baAlert/my/list/page'), {
				params : {
					p0 : offset,
					p1 : limit
				}
			})
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
			});
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
			});
		}
	}
});