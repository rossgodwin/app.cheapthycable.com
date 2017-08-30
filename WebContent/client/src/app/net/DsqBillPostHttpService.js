define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$http', srvc];
	
	function srvc($window, $http) {
		return {
			createPost : createPost
		};
		
		function createPost(billId, dsqPostId) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/dsqBillPost/create'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : billId,
			    	p1 : dsqPostId
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