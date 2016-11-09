define([
	'app/res/AppConsts',
	'app/gu/bill/create/BillCreateModule',
	'app/gu/intro/BillCreateCtrlr'
], function(
	appConsts,
	BillCreateModule,
	BillCreateCtrlr) {
	var moduleName = 'app.gu.intro.bill.create';
	var baseState = 'app.gu.intro';
	
	var module = angular.module(moduleName, ['app.gu.bill.create']);
	
	module.value('introBillCreateBaseState', baseState);
	
	module.controller('IntroBillCreateCtrlr', BillCreateCtrlr);
	
	var authorizeResolve = {
		authorize : ['AuthService', function(AuthService) {
			return AuthService.authorize(['USER']);
		}]
	};
	
	module.config(['$stateProvider', function($stateProvider) {
		$stateProvider
		.state(baseState + '.create', {
			templateUrl : 'client/src/app/gu/intro/bill-create.tpl.html',
			controller : 'IntroBillCreateCtrlr',
			controllerAs : 'ctrlr'
		})
		.state(baseState + '.create.node0', {
			params : {
				review : false
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node0.tpl.html',
			controller : 'BillCreateNode0Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node1', {
			params : {
				review : false
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node1.tpl.html',
			controller : 'BillCreateNode1Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node2', {
			params : {
				review : false
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node2.tpl.html',
			controller : 'BillCreateNode2Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node3', {
			params : {
				review : false
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node3.tpl.html',
			controller : 'BillCreateNode3Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node4', {
			params : {
				review : false
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node4.tpl.html',
			controller : 'BillCreateNode4Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node5', {
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node5.tpl.html',
			controller : 'BillCreateNode5Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node6', {
			params : {
				myBill : {}
			},
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node6.tpl.html',
			controller : 'BillCreateNode6Ctrlr',
			controllerAs : 'ctrlr',
			resolve : authorizeResolve
		})
		.state(baseState + '.create.node7', {
			templateUrl : 'client/src/app/gu/bill/create/view/bill-create-node7.tpl.html',
			controller : 'BillCreateNode7Ctrlr',
			controllerAs : 'ctrlr',
			resolve : {
				authorize : authorizeResolve.authorize,
				receiveBillAlerts : ['$q', 'authorize', 'spinnerService', 'BaAlertHttpService', function($q, authorize, spinnerService, BaAlertHttpService) {
					spinnerService.show(appConsts.screenLoadingSpinner);
					
					var defer = $q.defer();
					BaAlertHttpService.receiveAlerts().then(function(result) {
						spinnerService.hide(appConsts.screenLoadingSpinner);
						
						defer.resolve(result.data.result);
					});
					return defer.promise;
				}]
			}
		})
	}]);
	
	return module;
});