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
		vm.getServices = getServices;
		vm.removeServices = removeServices;
		vm.showCableOptionsBoxCount = showCableOptionsBoxCount;
		vm.removeCableOptionsBoxCount = removeCableOptionsBoxCount;
		vm.showCableOptionsDvrCount = showCableOptionsDvrCount;
		vm.removeCableOptionsDvrCount = removeCableOptionsDvrCount;
		vm.showCableOptionsSpecialChannels = showCableOptionsSpecialChannels;
		vm.removeCableOptionsSpecialChannels = removeCableOptionsSpecialChannels;
		
		function removeTotalAmount() {
			data.critrExactTotalAmount = '';
		}
		
		function removeProvider() {
			data.critrProvider = null;
		}
		
		function removeProviderName() {
			data.critrProviderName = '';
		}
		
		function getServices() {
			var services = [];
			if (data.critrInternetService) {
				services.push('Internet');
			}
			if (data.critrCableService) {
				services.push('Cable');
			}
			if (data.critrPhoneService) {
				services.push('Phone');
			}
			var s = services.join(', ');
			return s;
		}
		
		function removeServices() {
			data.critrInternetService = null;
			data.critrCableService = null;
			data.critrPhoneService = null;
		}
		
		function showCableOptionsBoxCount() {
			var r = data.critrCableService && data.critrCableOptionsBoxCount && data.critrCableOptionsBoxCount != data.cableOptionsBoxNAOption;
			return r;
		}
		
		function removeCableOptionsBoxCount() {
			data.critrCableOptionsBoxCount = data.cableOptionsBoxNAOption;
		}
		
		function showCableOptionsDvrCount() {
			var r = data.critrCableService && data.critrCableOptionsDvrCount && data.critrCableOptionsDvrCount != data.cableOptionsDvrNAOption;
			return r;
		}
		
		function removeCableOptionsDvrCount() {
			data.critrCableOptionsDvrCount = data.cableOptionsDvrNAOption;
		}
		
		function showCableOptionsSpecialChannels() {
			var r = data.critrCableService && data.critrCableOptionsSpecialChannels && data.critrCableOptionsSpecialChannels;
			return r;
		}
		
		function removeCableOptionsSpecialChannels() {
			data.critrCableOptionsSpecialChannels = null;
		}
	}
});