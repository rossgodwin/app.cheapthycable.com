define([
	'app/gu/dashboard/bill/comments/BillCommentsVwrBillTileDrctv',
	'app/gu/dashboard/bill/comments/BillCommentsVwrCtrlr'
], function(
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
			url : '/{billId}/comments',
			templateUrl : 'client/src/app/gu/dashboard/bill/comments/bill-comments-vwr.tpl.html',
			controller : 'BillCommentsVwrCtrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize,
				commentBill : ['authorize', '$q', '$stateParams', 'BillHttpService', function(authorize, $q, $stateParams, BillHttpService) {
					var defer = $q.defer();
					BillHttpService.getBillDetail($stateParams.billId).then(function(result) {
						defer.resolve(result.data.result);
					});
					return defer.promise;
				}]
			}
		})
	}]);
	
	return module;
});