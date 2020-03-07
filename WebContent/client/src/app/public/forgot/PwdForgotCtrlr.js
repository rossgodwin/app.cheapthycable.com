define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$window', '$state', 'gRecaptcha', 'spinnerService', 'AuthHttpService', 'RecaptchaHttpService', ctrlr];
	
	function ctrlr($window, $state, gRecaptcha, spinnerService, AuthHttpService, RecaptchaHttpService) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.email = '';
		
		// public functions
		vm.getProdName = getProdName;
		vm.getCssClasses = getCssClasses;
		vm.pwdForgot = pwdForgot;
		vm.goToLogin = goToLogin;
		
		vm.serverErrs.length = 0;
		vm.forgotBtnDisabled = true;
		
		var recaptchaSiteKey = $window.recaptchaSiteKey;
		var recaptchaAction = 'forgotPasswordPage';
		var recaptchaToken = '';
		
		gRecaptcha.initialize({key: recaptchaSiteKey}).then(function() {
			gRecaptcha.execute({action: recaptchaAction}).then(function (token) {
				recaptchaToken = token;
				RecaptchaHttpService.verify(token, recaptchaAction).then(function(result) {
					if (!result.safe) {
						vm.serverErrs.push(result.message);
					} else {
						vm.forgotBtnDisabled = false;
					}
				});
			});
		});
		
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