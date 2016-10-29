define([
	'app/net/AuthHttpService',
	'app/net/BillHttpService',
	'app/net/GeoZipCodeHttpService',
	'app/net/ProviderHttpService'
], function(
	AuthHttpService,
	BillHttpService,
	GeoZipCodeHttpService,
	ProviderHttpService) {
	var module = angular.module('app.net', []);
	
	module.factory('AuthHttpService', AuthHttpService);
	module.factory('BillHttpService', BillHttpService);
	module.factory('GeoZipCodeHttpService', GeoZipCodeHttpService);
	module.factory('ProviderHttpService', ProviderHttpService);
	
	return module;
});