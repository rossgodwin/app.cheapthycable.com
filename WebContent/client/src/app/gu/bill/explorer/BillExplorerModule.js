define([
	'src/app/gu/bill/explorer/BillExplorerDrctv',
	'src/app/gu/bill/explorer/BillExplorerListItemTileDrctv',
	'src/app/gu/bill/explorer/BillExplorerDataFctry',
	'src/app/gu/bill/explorer/BillExplorerSearchCtrlr',
	'src/app/gu/bill/explorer/BillExplorerSearchFilterCtrlr',
	'src/app/gu/bill/explorer/BillExplorerResultsCtrlr'
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