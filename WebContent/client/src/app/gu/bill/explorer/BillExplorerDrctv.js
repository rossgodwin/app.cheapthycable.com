define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				startBill : '='
			},
			templateUrl : "client/src/app/gu/bill/explorer/bill-explorer.drctv.tpl.html",
			controller : ['$scope', 'billExplorerData', ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];
	
	function ctrlr($scope, data) {
		var vm = this;
		
		init();
		
		function init() {
			data.setLatestBill(vm.startBill);
		}
	}
});