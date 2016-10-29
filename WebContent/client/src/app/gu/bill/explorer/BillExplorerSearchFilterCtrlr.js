define([], function() {
	return ['billExplorerData', ctrlr];
	
	function ctrlr(data) {
		var vm = this;
		
		// public variables
		vm.data = data;
		
		// public functions
		vm.removeTotalAmount = removeTotalAmount;
		vm.removeProvider = removeProvider;
		vm.removeProviderName = removeProviderName;
		
		function removeTotalAmount() {
			data.critrExactTotalAmount = '';
		}
		
		function removeProvider() {
			data.critrProvider = null;
		}
		
		function removeProviderName() {
			data.critrProviderName = '';
		}
	}
});