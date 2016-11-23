define([
	'src/app/net/AuthHttpService',
	'src/app/net/BaAlertHttpService',
	'src/app/net/BillHttpService',
	'src/app/net/DsqBillPostHttpService',
	'src/app/net/GeoZipCodeHttpService',
	'src/app/net/ProviderHttpService'
], function(
	AuthHttpService,
	BaAlertHttpService,
	BillHttpService,
	DsqBillPostHttpService,
	GeoZipCodeHttpService,
	ProviderHttpService) {
	var module = angular.module('app.net', []);
	
	module.factory('AuthHttpService', AuthHttpService);
	module.factory('BaAlertHttpService', BaAlertHttpService);
	module.factory('BillHttpService', BillHttpService);
	module.factory('DsqBillPostHttpService', DsqBillPostHttpService);
	module.factory('GeoZipCodeHttpService', GeoZipCodeHttpService);
	module.factory('ProviderHttpService', ProviderHttpService);
	
	return module;
});