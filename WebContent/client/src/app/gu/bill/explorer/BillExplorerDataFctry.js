/**
 * http://stackoverflow.com/questions/15800454/angularjs-the-correct-way-of-binding-to-a-service-properties
 */
define([], function() {
	var data = {};
	var defaultData = {
		reset : reset,
		mileRadius : 50,
		zipCode : '',
		critrExactTotalAmount : '',
		critrProvider : null,
		critrProviderName : '',
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
		data.zipCode = bill.geoZipCode.zipCode;
	}
});