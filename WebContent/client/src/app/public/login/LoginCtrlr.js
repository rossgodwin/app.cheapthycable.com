define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$state', 'AuthHttpService', ctrlr];
	
	function ctrlr($window, $state, AuthHttpService) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.user = {
//			email : 'tm.anonymous.1+xcbluser-34050@gmail.com',
//			password : 'asdfasdf4@'
			email : '',
			password : ''
		};
		
		// public functions
		vm.getCssClasses = getCssClasses;
		vm.login = login;
		vm.goToForgot = goToForgot;
		vm.goToCreateAccount = goToCreateAccount;
		
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
		    		$window.location.href = appUris.getAppUrl();
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