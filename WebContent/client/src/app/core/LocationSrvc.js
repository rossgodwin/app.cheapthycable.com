define([], function() {
	return ['$location', srvc];
	
	function srvc($location) {
		return {
			getBaseUrl : getBaseUrl
		};
		
		function getBaseUrl() {
			return $location.protocol() + '://' + $location.host() + ':' + $location.port();
		}
	}
})