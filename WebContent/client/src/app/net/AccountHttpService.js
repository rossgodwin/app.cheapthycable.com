define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			receiveLowerBillAlerts : receiveLowerBillAlerts,
			billAlertRegister : billAlertRegister
		};
		
		function receiveLowerBillAlerts() {
			return $http.get(appUris.getRestUrl('/account/receiveLowerBillAlerts'), null);
		}
		
		function billAlertRegister(email) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/account/billAlertRegister'),
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
	}
});