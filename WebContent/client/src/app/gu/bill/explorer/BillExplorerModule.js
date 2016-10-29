define([
	'app/gu/bill/explorer/BillExplorerDrctv',
	'app/gu/bill/explorer/BillExplorerListItemTileDrctv',
	'app/gu/bill/explorer/BillExplorerDataFctry',
	'app/gu/bill/explorer/BillExplorerSearchCtrlr',
	'app/gu/bill/explorer/BillExplorerSearchFilterCtrlr',
	'app/gu/bill/explorer/BillExplorerResultsCtrlr'
], function(
	BillExplorerDrctv,
	BillExplorerListItemTileDrctv,
	BillExplorerDataFctry,
	BillExplorerSearchCtrlr,
	BillExplorerSearchFilterCtrlr,
	BillExplorerResultsCtrlr
) {
	var module = angular.module('app.gu.bill.explorer', []);
	
	module.directive('guBillExplorer', BillExplorerDrctv);
	module.directive('guBillExplorerListItemTile', BillExplorerListItemTileDrctv);
	
	module.factory('billExplorerData', BillExplorerDataFctry);
	
	module.controller('BillExplorerSearchCtrlr', BillExplorerSearchCtrlr);
	module.controller('BillExplorerSearchFilterCtrlr', BillExplorerSearchFilterCtrlr);
	module.controller('BillExplorerResultsCtrlr', BillExplorerResultsCtrlr);
	
	return module;
});