define([], function() {
	return ['$scope', 'billExplorerData', 'ProviderHttpService', ctrlr];
	
	function ctrlr($scope, data, ProviderHttpService) {
		var vm = this;
		
		// public variables
		vm.providerOptionNone = new Object();
		vm.mileRadiusOptions = [10, 25, 50, 75, 100, 150, 200];
		vm.providerOptions = [];
		vm.sortOptions = [{
			label : 'Amount - High to Low',
			iconClass : 'glyphicon-sort-by-attributes-alt',
			option : 'TOTAL_AMOUNT',
			order : 'DESC'
		}, {
			label : 'Amount - Low to High',
			iconClass : 'glyphicon-sort-by-attributes',
			option : 'TOTAL_AMOUNT',
			order : 'ASC'
		}];
		vm.data = data;
		
		// public functions
		vm.onMileRadiusSelect = onMileRadiusSelect;
		vm.onAmountSelect = onAmountSelect;
		vm.getProviders = getProviders;
		vm.onProviderNameApply = onProviderNameApply;
		vm.onProviderSelect = onProviderSelect;
		vm.onSortSelect = onSortSelect;
		vm.getCritrSortLabel = getCritrSortLabel;
		
		init();
		
		function init() {
			vm.providerOptionNone.name = 'None';
			
			$scope.$watch(function () { return data.critrProviderName; }, function (critrProviderName) {
				vm.critrProviderName = data.critrProviderName;
			}, true);
			
//			ProviderHttpService.getProviderList('').then(function(response) {
//				var r = [];
//				var providers = response.data.result;
//				vm.providerOptions.push(vm.providerOptionNone);
//				for (var i = 0; i < providers.length; i++) {
//					vm.providerOptions.push(providers[i]);
//				}
//			}).catch(function(error) {
//			});
		}
		
		function onMileRadiusSelect(mileRadius) {
			data.mileRadius = mileRadius;
		}
		
		function onAmountSelect(amount) {
			data.critrExactTotalAmount = amount;
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
		
		function onProviderNameApply() {
			data.critrProviderName = vm.critrProviderName;
		}
		
		function onProviderSelect(provider) {
			if (provider.name == vm.providerOptionNone.name) {
				data.critrProvider = '';
			} else {
				data.critrProvider = provider;
			}
		}
		
		function onSortSelect(sort) {
			data.critrSort.option = sort.option;
			data.critrSort.order = sort.order;
		}
		
		function getCritrSortLabel() {
			var rslt = '';
			if (data.critrSort.option == 'TOTAL_AMOUNT') {
				rslt += 'Amount';
			}
			rslt += ' - ';
			if (data.critrSort.order == 'ASC') {
				rslt += 'Low to High';
			} else {
				rslt += 'High to Low';
			}
			return rslt;
		}
	}
});