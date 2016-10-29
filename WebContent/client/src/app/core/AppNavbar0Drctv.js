define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				navbarAppName : '=',
				navbarLogoUrl : '='
			},
			templateUrl : "client/src/app/core/app-navbar0.drctv.tpl.html",
			controller : [ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true
		};
	}];

	function ctrlr() {
		var vm = this;
	};
});