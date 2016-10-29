// TODO rename this to BillUtils and adjust so this isn't a service but rather just javascript see GeoZipCodeUtils
define([], function() {
	return {
		getProviderName : getProviderName,
		getServicesStr : getServicesStr,
		getInternetOptionsStr : getInternetOptionsStr,
		getCableOptionsStr : getCableOptionsStr,
		getAllOptionsStr : getAllOptionsStr,
		addServices : addServices,
		renderInternetDownloadSpeedMbps : renderInternetDownloadSpeedMbps,
		renderInternetDownloadSpeedMbpsRng : renderInternetDownloadSpeedMbpsRng
	};
	
	function getProviderName(bill) {
		if (bill.provider) {
			return bill.provider.name;
		} else {
			return bill.providerOther;
		}
	}
	
	function getServicesStr(bill, joinChar) {
		var services = [];
		addServices(bill, services);
		var s = services.join(joinChar);
		return s;
	}
	
	function getInternetOptionsStr(bill, joinChar) {
		var options = [];
		addInternetOptions(bill, options);
		var s = options.join(joinChar);
		return s;
	}
	
	function getCableOptionsStr(bill, joinChar) {
		var options = [];
		addCableOptions(bill, options);
		var s = options.join(joinChar);
		return s;
	}
	
	function getAllOptionsStr(bill, joinChar) {
		var options = [];
		addInternetOptions(bill, options);
		addCableOptions(bill, options);
		var s = options.join(joinChar);
		return s;
	}
	
	function addServices(bill, services) {
		if (bill.internetService) {
			services.push('Internet');
		}
		if (bill.cableService) {
			services.push('Cable');
		}
		if (bill.phoneService) {
			services.push('Phone');
		}
	}
	
	function addInternetOptions(bill, options) {
		if (angular.isDefined(bill.internetOptions)) {
			if (bill.internetOptions.modem) {
				options.push('Modem');
			}
			
//			if (bill.internetOptions.downloadSpeedMbps) {
//				options.push(bill.internetOptions.downloadSpeedMbps + ' Mbps down');
//			}
			var dldSpeedMbpsRngLower = bill.internetOptions.downloadSpeedMbpsRngLower;
			var dldSpeedMbpsRngUpper = bill.internetOptions.downloadSpeedMbpsRngUpper;
			if (dldSpeedMbpsRngLower && dldSpeedMbpsRngUpper) {
				options.push(renderInternetDownloadSpeedMbpsRng(dldSpeedMbpsRngLower, dldSpeedMbpsRngUpper, true) + ' down');
			}
		}
	}
	
	function addCableOptions(bill, options) {
		if (angular.isDefined(bill.cableOptions)) {
			var dvrCount = bill.cableOptions.dvrCount;
			if (dvrCount) {
				options.push(dvrCount + ' DVR' + (dvrCount > 1 ? 's' : ''));
			}
			
			var boxCount = bill.cableOptions.boxCount;
			if (boxCount) {
				options.push(boxCount + ' Box' + (boxCount > 1 ? 'es' : ''));
			}
			
			var specialChannels = bill.cableOptions.specialChannels;
			if (specialChannels) {
				options.push('Special Channels');
			}
		}
	}
	
	function renderInternetDownloadSpeedMbps(internetOptions, includeSuffix) {
		return renderInternetDownloadSpeedMbpsRng(internetOptions.downloadSpeedMbpsRngLower, internetOptions.downloadSpeedMbpsRngUpper, includeSuffix);
	}
	
	function renderInternetDownloadSpeedMbpsRng(lower, upper, includeSuffix) {
		var s = lower + ' - ' + upper;
		if (includeSuffix) {
			s += ' Mbps';
		}
		return s;
	}
});