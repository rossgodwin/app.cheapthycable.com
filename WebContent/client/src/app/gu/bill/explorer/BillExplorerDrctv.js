define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				startBill : '='
			},
			templateUrl : "client/src/app/gu/bill/explorer/bill-explorer.drctv.tpl.html",
			controller : ['$scope', 'billExplorerData', 'BillHttpService', ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];
	
	function ctrlr($scope, data, BillHttpService) {
		var vm = this;
		
		init();
		
		function init() {
			$scope.$watch(function () { return data.mileRadius; }, function (mileRadius) {
				updateTotals();
			}, true);
			
			data.setLatestBill(vm.startBill);
		}
		
		function updateTotals() {
			BillHttpService.getBillExplorerResults(data.zipCode, data.mileRadius).then(function(response) {
				var result = response.data.result;
				
				data.lowestTotalAmount = result.lowestTotalAmount;
				data.averageTotalAmount = result.averageTotalAmount;
				data.highestTotalAmount = result.highestTotalAmount;
			}).catch(function(error) {
				// TODO
			});
		}
	}
});