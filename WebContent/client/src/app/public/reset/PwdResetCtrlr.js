define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$window', '$state', '$stateParams', 'spinnerService', 'AuthHttpService', ctrlr];
	
	function ctrlr($window, $state, $stateParams, spinnerService, AuthHttpService) {
		var vm = this;
		var token = $stateParams.token;
		
		// public variables
		vm.serverErrs = [];
		vm.pwdMinLength = $window.pwdMinLength;
		vm.password = '';
		vm.passwordConfirm = '';
		
		// public functions
		vm.getCssClasses = getCssClasses;
		vm.pwdReset = pwdReset;
		
		function getCssClasses(formCtrlr, modelCtrlr) {
			return {
		    	'has-error': modelCtrlr.$invalid && (modelCtrlr.$dirty || formCtrlr.$submitted),
		    	'has-success': modelCtrlr.$valid
		    };
		};
		
		function pwdReset(formCtrlr) {
			vm.serverErrs.length = 0;
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			AuthHttpService.pwdReset(token, vm.password).then(function(result) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (result.data.resultCode === 1) {
					$state.go('app.reset-success');
		    	} else {
		    		var errs = result.data.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
		    	}
			});
		}
	}
});