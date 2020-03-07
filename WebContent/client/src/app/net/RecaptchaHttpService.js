define(['src/app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			verify : verify
		};
		
		function verify(token, action) {
			return $http({
				method : 'POST',
			    url : appUris.getRestUrl('/recaptcha/verify'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			    	var str = [];
			    	for (var p in obj) {
			    		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			    	}
			    	return str.join("&");
			    },
			    data : {
			    	p0: token,
			    	p1: action
			    }
			}).then(c)
			.catch(f);
			
			function c(response) {
				return response.data.result;
			}
			
			function f(error) {
				// TODO
			}
		}
	};
});