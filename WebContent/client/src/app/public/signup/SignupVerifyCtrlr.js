define([], function() {
	return ['$stateParams', ctrlr];
	
	function ctrlr($stateParams) {
		var vm = this;
		
		// public variables
		vm.email = $stateParams.email;
	}
});