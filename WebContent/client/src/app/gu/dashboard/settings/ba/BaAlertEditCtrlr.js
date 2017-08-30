define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$state', '$stateParams', 'baAlertModel', 'spinnerService', 'BaAlertHttpService', ctrlr];
	
	function ctrlr($state, $stateParams, model, spinnerService, BaAlertHttpService) {
		var vm = this;
		
		// public variables
		vm.titlebarText = $stateParams.titlebarText;
		vm.frequencyDayOptions = [7, 14, 21];
		vm.mileRadiusOptions = [25, 50, 75];
		vm.errs = [];
		vm.model = model;
		
		// public functions
		vm.titlebarBack = back;
		vm.save = save;
		
		init();
		
		function init() {
			model.reset();
		}
		
		function back() {
			$state.go('app.gu.ba.mgt', {}, {location : false});
		}
		
		function validate(onSuccess) {
			vm.errs = [];
			if (!vm.model.critrAmountBelow) {
				vm.errs.push('Amount below is required');
			} else {
				if (Number(vm.model.critrAmountBelow) <= 0) {
					vm.errs.push('Please enter a total amount > 0');
				}
			}
			if (vm.errs.length == 0) {
				onSuccess();
			}
		}
		
		function save() {
			validate(function() {
				spinnerService.show(appConsts.screenLoadingSpinner);
				
				BaAlertHttpService.saveOrUpdateAlert(vm.model).then(function(response) {
					if (!angular.isUndefined(response)) {
						if (response.data.resultCode === 1) {
							back();
						}
					}
					spinnerService.hide(appConsts.screenLoadingSpinner);
				});
			});
		}
	}
});