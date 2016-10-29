define([], function() {
	return {
		getCityStateZipCode : getCityStateZipCode
	};
	
	function getCityStateZipCode(obj) {
		var r = obj.city + ', ' + obj.stateCode + ' ' + obj.zipCode;
		return r;
	}
});