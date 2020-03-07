define([
	'src/app/net/AuthHttpService',
	'src/app/net/BaAlertHttpService',
	'src/app/net/BillHttpService',
	'src/app/net/DsqBillPostHttpService',
	'src/app/net/GeoZipCodeHttpService',
	'src/app/net/ProviderHttpService',
	'src/app/net/RecaptchaHttpService'
], function(
	AuthHttpService,
	BaAlertHttpService,
	BillHttpService,
	DsqBillPostHttpService,
	GeoZipCodeHttpService,
	ProviderHttpService,
	RecaptchaHttpService) {
	var module = angular.module('app.net', []);
	
	module.factory('AuthHttpService', AuthHttpService);
	module.factory('BaAlertHttpService', BaAlertHttpService);
	module.factory('BillHttpService', BillHttpService);
	module.factory('DsqBillPostHttpService', DsqBillPostHttpService);
	module.factory('GeoZipCodeHttpService', GeoZipCodeHttpService);
	module.factory('ProviderHttpService', ProviderHttpService);
	module.factory('RecaptchaHttpService', RecaptchaHttpService);
	
	return module;
});