define([
	'src/app/utils/GeoZipCodeUtils'
], function(GeoZipCodeUtils) {
	return ['$stateParams', 'billCreateNavHlpr', 'billCreateModel', 'ProviderHttpService', ctrlr];
	
	function ctrlr($stateParams, navHlpr, model, ProviderHttpService) {
		var vm = this;
		
		// public variables
		vm.visited = false;
		vm.review = $stateParams.review;
		vm.bill = model;
		vm.errs = [];
		
		// public functions
		vm.getProviders = getProviders;
		vm.getArea = getArea;
		vm.prev = prev;
		vm.next = next;
		vm.finish = finish;
		
		init();
		
		function init() {
			if (vm.bill.lookupProviderName || vm.review) {
				vm.visited = true;
				vm.lookupProviderName = vm.bill.lookupProviderName;
			}
		}
		
		function getProviders(value) {
			return ProviderHttpService.getProviderList(value).then(function(response) {
				var r = [];
				var providers = response.data.result;
				for (var i = 0; i < providers.length; i++) {
					r.push(providers[i].name);
				}
				return r;
			}).catch(function(error) {
				// TODO
			});
		}
		
		function getArea() {
			return GeoZipCodeUtils.getCityStateZipCode(vm.bill.geoZipCode);
		}
		
		function validate() {
			vm.errs = [];
			if (!vm.lookupProviderName) {
				vm.errs.push('Please tell us the company that is billing you.');
			}
		}
		
		function updateModel() {
			vm.bill.lookupProviderName = vm.lookupProviderName;
		}
		
		function prev() {
			navHlpr.goToPrevState(navHlpr.getBaseState() + '.create.node0');
		}
		
		function next() {
			validate();
			if (vm.errs.length == 0) {
				updateModel();
				navHlpr.goToNextState(navHlpr.getBaseState() + '.create.node2');
			}
		}
		
		function finish() {
			validate();
			if (vm.errs.length == 0) {
				updateModel();
				$stateParams.review = false;
				navHlpr.goToState(navHlpr.getBaseState() + '.create.node5');
			}
		}
	};
});