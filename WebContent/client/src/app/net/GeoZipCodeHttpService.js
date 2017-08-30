define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$http', srvc];
	
	function srvc($window, $http) {
		var service = {
			isZipCodeValid : isZipCodeValid,
			getGeoZipCode : getGeoZipCode
		};
		
		return service;
		
		function isZipCodeValid(zipCode) {
			return $http.get(appUris.getRestUrl('/geoZipCode/zipCode/' + zipCode + '/validate'), {
				params : {
				}
			}).catch(errorCallback);
		}
		
		function getGeoZipCode(zipCode) {
			return $http.get(appUris.getRestUrl('/geoZipCode/zipCode/' + zipCode), {
				params : {
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