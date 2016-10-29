define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				onSelect : '&',
				iconClass : '@',
				label : '@',
				description : '@'
			},
			templateUrl : "client/src/app/core/app-icon-text-btn0.drctv.tpl.html",
			controller : [ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr() {
		var vm = this;
	};
});