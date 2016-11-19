define(['app/utils/BillUtils'], function(BillUtils) {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				bill : '='
			},
			templateUrl : "client/src/app/gu/dashboard/bill/comments/bill-comments-vwr-bill-tile.drctv.tpl.html",
			controller : [ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr() {
		var vm = this;
		
		// public functions
		vm.getServicesStr = getServicesStr;
		vm.getAllOptionsStr = getAllOptionsStr;
		
		function getServicesStr() {
			var r = BillUtils.getServicesStr(vm.bill, ', ');
			return r;
		}
		
		function getAllOptionsStr() {
			var r = BillUtils.getAllOptionsStr(vm.bill, ', ');
			return r;
		}
	};
});