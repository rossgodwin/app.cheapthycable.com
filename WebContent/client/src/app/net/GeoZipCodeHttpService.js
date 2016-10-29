define([
	'app/res/AppUris'
], function(uris) {
	var service = function($http) {
		var service = {
			isZipCodeValid : isZipCodeValid,
			getGeoZipCode : getGeoZipCode
		};
		
		return service;
		
		function isZipCodeValid(zipCode) {
			return $http.get(uris.getRestUrl('/geoZipCode/zipCode/' + zipCode + '/validate'), {
				params : {
				}
			});
		}
		
		function getGeoZipCode(zipCode) {
			return $http.get(uris.getRestUrl('/geoZipCode/zipCode/' + zipCode), {
				params : {
				}
			});
		}
	};
	
	return ['$http', service];
});