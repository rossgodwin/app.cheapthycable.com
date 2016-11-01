define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		var service = {
			isZipCodeValid : isZipCodeValid,
			getGeoZipCode : getGeoZipCode
		};
		
		return service;
		
		function isZipCodeValid(zipCode) {
			return $http.get(appUris.getRestUrl('/geoZipCode/zipCode/' + zipCode + '/validate'), {
				params : {
				}
			});
		}
		
		function getGeoZipCode(zipCode) {
			return $http.get(appUris.getRestUrl('/geoZipCode/zipCode/' + zipCode), {
				params : {
				}
			});
		}
	};
});