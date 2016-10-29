define([], function() {
	return ['AuthHttpService', drctv];
	
	function drctv(AuthHttpService) {
		return {
			require : 'ngModel',
			link : link
		};
		
		function link(scope, elm, attrs, ctrlr) {
			var original;
			var attr = 'signupUniqueEmailChk';
			
			ctrlr.$formatters.unshift(function(modelValue) {
				original = modelValue;
				return modelValue;
			});
			
			ctrlr.$parsers.push(function(viewValue) {
				if (viewValue && viewValue !== original) {
					AuthHttpService.isSignupUniqueEmail(viewValue).then(function(result) {
						ctrlr.$setValidity(attr, result);
					});
				}
				return viewValue;
			});
		}
	};
});