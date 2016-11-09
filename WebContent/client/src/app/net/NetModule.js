define([
	'app/net/AuthHttpService',
	'app/net/BaAlertHttpService',
	'app/net/BillHttpService',
	'app/net/GeoZipCodeHttpService',
	'app/net/ProviderHttpService'
], function(
	AuthHttpService,
	BaAlertHttpService,
	BillHttpService,
	GeoZipCodeHttpService,
	ProviderHttpService) {
	var module = angular.module('app.net', []);
	
	module.factory('AuthHttpService', AuthHttpService);
	module.factory('BaAlertHttpService', BaAlertHttpService);
	module.factory('BillHttpService', BillHttpService);
	module.factory('GeoZipCodeHttpService', GeoZipCodeHttpService);
	module.factory('ProviderHttpService', ProviderHttpService);
	
	return module;
});