define([
	'vendor/hashids/hashids.min',
	'src/app/gu/dashboard/bill/comments/BillCommentsVwrBillTileDrctv',
	'src/app/gu/dashboard/bill/comments/BillCommentsVwrCtrlr'
], function(
	Hashids,
	BillCommentsVwrBillTileDrctv,
	BillCommentsVwrCtrlr) {
	var module = angular.module('app.gu.dashboard.bill.comments', []);
	
	module.directive('billCommentsVwrBillTile', BillCommentsVwrBillTileDrctv);
	
	module.controller('BillCommentsVwrCtrlr', BillCommentsVwrCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.bill.comments', {
			url : '/{encBillId}/comments',
			templateUrl : 'client/src/app/gu/dashboard/bill/comments/bill-comments-vwr.tpl.html',
			controller : 'BillCommentsVwrCtrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize,
				commentBill : ['authorize', '$q', '$stateParams', 'BillHttpService', function(authorize, $q, $stateParams, BillHttpService) {
					var billId = new Hashids().decode($stateParams.encBillId);
					var defer = $q.defer();
					BillHttpService.getBillDetail(billId).then(function(result) {
						defer.resolve(result.data.result);
					});
					return defer.promise;
				}]
			}
		})
	}]);
	
	return module;
});