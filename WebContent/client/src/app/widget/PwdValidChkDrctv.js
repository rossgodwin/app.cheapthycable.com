define([], function() {
	return ['AuthHttpService', drctv];
	
	function drctv(AuthHttpService) {
		return {
			require : 'ngModel',
			link : link
		};
		
		function link(scope, elm, attrs, ctrlr) {
			var original;
			var attr = 'pwdValidChk';
			
			ctrlr.$formatters.unshift(function(modelValue) {
				original = modelValue;
				return modelValue;
			});
			
			ctrlr.$parsers.push(function(viewValue) {
				ctrlr.$setValidity(attr, true);
				if (viewValue && viewValue !== original) {
					AuthHttpService.isPwdValidChk(viewValue).then(function(result) {
						var errs = result.data.result;
						if (errs.length > 0) {
							ctrlr.$setValidity(attr, false);
							scope.errs = errs;
						} else {
							scope.errs = [];
						}
					});
				} else {
					scope.errs = [];
				}
				return viewValue;
			});
		}
	};
});