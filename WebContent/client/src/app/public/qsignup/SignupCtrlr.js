define(['src/app/res/AppConsts', 'src/app/res/AppUris'], function(appConsts, appUris) {
	return ['$window', '$state', 'gRecaptcha', 'spinnerService', 'SignupModel', 'AuthHttpService', 'RecaptchaHttpService', ctrlr];
	
	function ctrlr($window, $state, gRecaptcha, spinnerService, SignupModel, AuthHttpService, RecaptchaHttpService) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.pwdMinLength = $window.pwdMinLength;
		vm.signup = SignupModel;
		
		// public functions
		vm.getCssClasses = getCssClasses;
		vm.submit = submit;
		vm.goToLogin = goToLogin;
		
		vm.serverErrs.length = 0;
		vm.signupBtnDisabled = true;
		
		var recaptchaSiteKey = $window.recaptchaSiteKey;
		var recaptchaAction = 'signupPage';
		var recaptchaToken = '';
		
		gRecaptcha.initialize({key: recaptchaSiteKey}).then(function() {
			gRecaptcha.execute({action: recaptchaAction}).then(function (token) {
				recaptchaToken = token;
				RecaptchaHttpService.verify(token, recaptchaAction).then(function(result) {
					if (!result.safe) {
						vm.serverErrs.push(result.message);
					} else {
						vm.signupBtnDisabled = false;
					}
				});
			});
		});
		
		function getCssClasses(formCtrlr, modelCtrlr) {
			return {
		    	'has-error': modelCtrlr.$invalid && (modelCtrlr.$dirty || formCtrlr.$submitted),
		    	'has-success': modelCtrlr.$valid
		    };
		};
		
		function submit(formCtrlr) {
			vm.serverErrs.length = 0;
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			AuthHttpService.qsignup(vm.signup).then(function(response) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (response.data.resultCode === 1) {
		    		$window.location.href = appUris.getAppUrl();
		    	} else {
		    		var errs = response.data.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
		    	}
			}).catch(function(error) {
				// TODO
			});
		};
		
		function goToLogin() {
			$state.go('app.login');
		};
	};
})