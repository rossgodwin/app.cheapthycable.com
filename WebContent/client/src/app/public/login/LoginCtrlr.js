define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$state', '$stateParams', 'gRecaptcha', 'AuthHttpService', 'RecaptchaHttpService', ctrlr];
	
	function ctrlr($window, $state, $stateParams, gRecaptcha, AuthHttpService, RecaptchaHttpService) {
		var vm = this;
		var onLoginGo = $stateParams.go;
		
		// public variables
		vm.serverErrs = [];
		vm.user = {
			email : '',
			password : ''
		};
		
		// public functions
		vm.getCssClasses = getCssClasses;
		vm.login = login;
		vm.goToForgot = goToForgot;
		vm.goToCreateAccount = goToCreateAccount;
		
		vm.serverErrs.length = 0;
		vm.loginBtnDisabled = true;
		
		var recaptchaSiteKey = $window.recaptchaSiteKey;
		var recaptchaAction = 'loginPage';
		var recaptchaToken = '';
		
		gRecaptcha.initialize({key: recaptchaSiteKey}).then(function() {
			gRecaptcha.execute({action: recaptchaAction}).then(function (token) {
				recaptchaToken = token;
				RecaptchaHttpService.verify(token, recaptchaAction).then(function(result) {
					if (!result.safe) {
						vm.serverErrs.push(result.message);
					} else {
						vm.loginBtnDisabled = false;
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
		
		function login() {
			vm.serverErrs.length = 0;
		    
		    AuthHttpService.login(vm.user.email, vm.user.password).then(function(result) {
		    	if (result.data.resultCode === 1) {
		    		if (angular.isDefined(onLoginGo)) {
		    			$window.location.href = onLoginGo;
		    		} else {
		    			$window.location.href = appUris.getAppUrl();
		    		}
		    	} else {
		    		var errs = result.data.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
		    	}
		    });
		};
		
		function goToForgot() {
			$state.go('app.forgot');
		};
		
		function goToCreateAccount() {
			$state.go('app.signup');
		}
	};
})