define([
	'app/utils/GeoZipCodeUtils'
], function(GeoZipCodeUtils) {
	return ['$stateParams', 'billCreateNavHlpr', 'billCreateModel', 'GeoZipCodeHttpService', ctrlr];
	
	function ctrlr($stateParams, navHlpr, model, GeoZipCodeHttpService) {
		var vm = this;
		var checkZipCodePromise;
		
		// public variables
		vm.visited = false;
		vm.review = $stateParams.review;
		vm.bill = model;
		vm.errs = [];
		vm.zipCodeDetail = '';
		
		// public functions
		vm.asyncUpdateZipCodeDetail = asyncUpdateZipCodeDetail;
		vm.next = next;
		vm.finish = finish;
		
		init();
		
		function init() {
			if (vm.bill && vm.bill.zipCode) {
				vm.visited = true;
				vm.zipCode = vm.bill.zipCode;
				vm.totalAmount = vm.bill.totalAmount;
			}
		}
		
		function asyncUpdateZipCodeDetail() {
			GeoZipCodeHttpService.getGeoZipCode(vm.zipCode).then(function(response) {
				var geoZipCode = response.data.result;
				if (angular.isDefined(geoZipCode)) {
					vm.bill.geoZipCode = geoZipCode;
					updateZipCodeDetail();
				} else {
					vm.bill.geoZipCode = null;
					updateZipCodeDetail();
				}
			}).catch(function(error) {
				vm.bill.geoZipCode = null;
				updateZipCodeDetail();
				// TODO
			});
		}
		
		function updateZipCodeDetail() {
			if (angular.isDefined(vm.bill.geoZipCode) && vm.bill.geoZipCode != null) {
				vm.zipCodeDetail = GeoZipCodeUtils.getCityStateZipCode(vm.bill.geoZipCode);
			} else {
				vm.zipCodeDetail = '';
			}
		}
		
		function validate(onSuccess) {
			vm.errs = [];
			if (!vm.zipCode) {
				vm.errs.push('Zip code is required');
			} else {
				GeoZipCodeHttpService.isZipCodeValid(vm.zipCode).then(function(response) {
					onSuccess(response.data.result);
				}).catch(function(error) {
					// TODO
				});
			}
			if (!vm.totalAmount) {
				vm.errs.push('Total amount is required');
			} else {
				if (Number(vm.totalAmount) <= 0) {
					vm.errs.push('Please enter a total amount > 0');
				}
			}
		}
		
		function updateModel() {
			vm.bill.zipCode = vm.zipCode;
			vm.bill.totalAmount = vm.totalAmount;
		}
		
		function next() {
			validate(function(result) {
				if (!result) {
					vm.errs.unshift('Zip code is not valid');
				}
				
				if (vm.errs.length == 0) {
					updateModel();
					navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node1');
				}
			});
		}
		
		function finish() {
			updateModel();
			$stateParams.review = false;
			navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node5');
		}
	};
});