/**
 * http://stackoverflow.com/questions/30895140/nested-promises-in-ui-router-resolve
 */
define([
	'src/app/gu/bill/mgt/BillMgtListItemTileDrctv',
	'src/app/gu/bill/mgt/BillMgtCtrlr',
	'src/app/gu/dashboard/bill/BillCreateModule',
	'src/app/gu/dashboard/bill/BillExplorerModule',
	'src/app/gu/dashboard/bill/comments/BillCommentsModule'
], function(
	BillMgtListItemTileDrctv,
	BillMgtCtrlr,
	BillCreateModule,
	BillExplorerModule,
	BillCommentsModule) {
	var module = angular.module('app.gu.dashboard.bill', ['app.gu.dashboard.bill.create', 'app.gu.dashboard.bill.explorer', 'app.gu.dashboard.bill.comments']);
	
	module.value('newBillStateName', 'app.gu.bill.create.node0');
	
	module.directive('guBillMgtListItemTile', BillMgtListItemTileDrctv);
	
	module.controller('BillMgtCtrlr', BillMgtCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state('app.gu.bill', {
			url : '/bill',
			abstract : true,
			template: '<ui-view/>'
		})
		.state('app.gu.bill.mgt', {
			url : '/manage',
			templateUrl : 'client/src/app/gu/bill/mgt/bill-mgt.tpl.html',
			controller : 'BillMgtCtrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
	}]);
	
	return module;
});