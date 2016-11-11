define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			receiveAlerts : receiveAlerts,
			addDefaultAlert : addDefaultAlert,
			getMyAlertListPage : getMyAlertListPage,
			deleteAlert : deleteAlert
		};
		
		function receiveAlerts() {
			return $http.get(appUris.getRestUrl('/baAlert/receiveAlerts'), null);
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
		
		function deleteAlert(alertId) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/baAlert/' + alertId + '/delete'),
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
	}
});