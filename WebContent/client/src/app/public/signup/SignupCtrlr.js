define(['app/res/AppConsts'], function(	appConsts) {
	return ['$window', '$state', 'spinnerService', 'SignupModel', 'AuthHttpService', ctrlr];
	
	function ctrlr($window, $state, spinnerService, SignupModel, AuthHttpService) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.pwdMinLength = $window.pwdMinLength;
		vm.signup = SignupModel;
		
		// public functions
		vm.getCssClasses = getCssClasses;
		vm.submit = submit;
		vm.goToLogin = goToLogin;
		
		function getCssClasses(formCtrlr, modelCtrlr) {
			return {
		    	'has-error': modelCtrlr.$invalid && (modelCtrlr.$dirty || formCtrlr.$submitted),
		    	'has-success': modelCtrlr.$valid
		    };
		};
		
		function submit(formCtrlr) {
			vm.serverErrs.length = 0;
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			AuthHttpService.signup(vm.signup).then(function(result) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (result.resultCode === 1) {
					$state.go('app.signup-verify', {email : vm.signup.email}, {});
				} else {
					var errs = result.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
				}
			});
		};
		
		function goToLogin() {
			$state.go('app.login');
		};
	};
})