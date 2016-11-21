define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$window', '$state', 'spinnerService', 'AuthHttpService', ctrlr];
	
	function ctrlr($window, $state, spinnerService, AuthHttpService) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.email = '';
		
		// public functions
		vm.getProdName = getProdName;
		vm.getCssClasses = getCssClasses;
		vm.pwdForgot = pwdForgot;
		vm.goToLogin = goToLogin;
		
		function getProdName() {
			return $window.appName;
		}
		
		function getCssClasses(formCtrlr, modelCtrlr) {
			return {
		    	'has-error': modelCtrlr.$invalid && (modelCtrlr.$dirty || formCtrlr.$submitted),
		    	'has-success': modelCtrlr.$valid
		    };
		};
		
		function pwdForgot(formCtrlr) {
			vm.serverErrs.length = 0;
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			AuthHttpService.pwdForgot(vm.email).then(function(result) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (result.data.resultCode === 1) {
					$state.go('app.forgot-success', {email : vm.email}, {});
		    	} else {
		    		var errs = result.data.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
		    	}
			});
		}
		
		function goToLogin() {
			$state.go('app.login');
		};
	};
})