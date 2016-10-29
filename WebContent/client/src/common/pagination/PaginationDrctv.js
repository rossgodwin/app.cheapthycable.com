define([], function() {
	return [function drctv() {
		return {
			restrict : 'E',
			scope : {
				currentPageNumber : '=',
				pageNumbers : '=',
				totalPages : '=',
				setPage : '&'
			},
			templateUrl : "client/src/common/pagination/pagination.drctv.tpl.html",
			controller : [ctrlr],
			controllerAs : 'ctrlr',
			bindToController : true,
			replace : true,
			link : function(scope, elm, attrs) {
				scope.callSetPage = function(page) {
					this.ctrlr.setPage()(page);
				}
			}
		};
	}];

	function ctrlr() {
		var vm = this;
	};
});