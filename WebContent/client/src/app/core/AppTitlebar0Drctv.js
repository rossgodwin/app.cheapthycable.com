define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				titlebarText : '=',
				titlebarBack : '&'
			},
			templateUrl : "client/src/app/core/app-titlebar0.drctv.tpl.html",
			controller : [ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr() {
		var vm = this;
	};
});