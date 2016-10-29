define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			templateUrl : "client/src/app/gu/bill/create/bill-create.drctv.tpl.html",
			controller : ['billCreateNavHlpr', 'billCreateModel', ctrlr],
			controllerAs : 'drctvCtrlr',
			bindToController : true
		};
	}];
	
	function ctrlr(navHlpr, model) {
		var vm = this;
		
		// public variables
		vm.navHlpr = navHlpr;
		
		init();
		
		function init() {
			navHlpr.resetProgress();
			model.reset();
		}
	}
});