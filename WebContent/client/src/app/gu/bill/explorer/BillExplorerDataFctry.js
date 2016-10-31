/**
 * http://stackoverflow.com/questions/15800454/angularjs-the-correct-way-of-binding-to-a-service-properties
 */
define([], function() {
	var data = {};
	var defaultData = {
		reset : reset,
		cableOptionsBoxNAOption : 'N/A',
		cableOptionsDvrNAOption : 'N/A',
		critrMileRadius : 50,
		critrZipCode : '',
		critrExactTotalAmount : '',
		critrProvider : null,
		critrProviderName : '',
		critrServicesSetOperator : 'MATCHES',
		critrInternetService : null,
		critrCableService : null,
		critrPhoneService : null,
		critrCableOptionsBoxCount : null,
		critrCableOptionsDvrCount : null,
		critrCableOptionsSpecialChannels : null,
		critrSort : {
			option : 'TOTAL_AMOUNT',
			order : 'DESC'
		},
		latestBill : {},
		setLatestBill : setLatestBill,
		lowestTotalAmount : '',
		averageTotalAmount : '',
		highestTotalAmount : ''
	}
	
	return function() {
		defaultData.reset();
		return data;
	};
	
	function reset() {
		return data = angular.extend(data, defaultData);
	}
	
	function setLatestBill(bill) {
		data.latestBill = bill;
		data.critrZipCode = bill.geoZipCode.zipCode;
		data.critrInternetService = bill.internetService;
		data.critrCableService = bill.cableService;
		data.critrPhoneService = bill.phoneService;
		data.critrCableOptionsBoxCount = data.cableOptionsBoxNAOption;
		data.critrCableOptionsDvrCount = data.cableOptionsDvrNAOption;
		if (bill.cableOptions) {
			if (bill.cableOptions.boxCount && bill.cableOptions.boxCount > 0) {
				data.critrCableOptionsBoxCount = bill.cableOptions.boxCount;
			}
			if (bill.cableOptions.dvrCount && bill.cableOptions.dvrCount > 0) {
				data.critrCableOptionsDvrCount = bill.cableOptions.dvrCount;
			}
//			if (!bill.cableOptions.boxCount || bill.cableOptions.boxCount == 0) {
//				data.critrCableOptionsBoxCount = data.cableOptionsBoxNAOption;
//			} else {
//				data.critrCableOptionsBoxCount = bill.cableOptions.boxCount;
//			}
//			if (!bill.cableOptions.dvrCount || bill.cableOptions.dvrCount == 0) {
//				data.critrCableOptionsDvrCount = data.cableOptionsDvrNAOption;
//			} else {
//				data.critrCableOptionsDvrCount = bill.cableOptions.dvrCount;
//			}
			data.critrCableOptionsSpecialChannels = bill.cableOptions.specialChannels;
		}
	}
});