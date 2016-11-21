define([
	'src/app/net/AuthHttpService',
	'src/app/net/BaAlertHttpService',
	'src/app/net/BillHttpService',
	'src/app/net/DsqBillCommentHttpService',
	'src/app/net/GeoZipCodeHttpService',
	'src/app/net/ProviderHttpService'
], function(
	AuthHttpService,
	BaAlertHttpService,
	BillHttpService,
	DsqBillCommentHttpService,
	GeoZipCodeHttpService,
	ProviderHttpService) {
	var module = angular.module('app.net', []);
	
	module.factory('AuthHttpService', AuthHttpService);
	module.factory('BaAlertHttpService', BaAlertHttpService);
	module.factory('BillHttpService', BillHttpService);
	module.factory('DsqBillCommentHttpService', DsqBillCommentHttpService);
	module.factory('GeoZipCodeHttpService', GeoZipCodeHttpService);
	module.factory('ProviderHttpService', ProviderHttpService);
	
	return module;
});