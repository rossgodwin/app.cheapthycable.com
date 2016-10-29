define([], function() {
	return [drctv];
	
	function drctv() {
		return {
			restrict : 'A',
			require : 'ngModel',
			link : link
		};
		
		function link(scope, elm, attrs, ctrlr) {
			function validateEqual(myValue, otherValue) {
				if (myValue === otherValue) {
					ctrlr.$setValidity('valuesEqualRqrd', true);
					return myValue;
				} else {
					ctrlr.$setValidity('valuesEqualRqrd', false);
					return undefined;
				}
			}

			scope.$watch(attrs.validateEquals, function(otherModelValue) {
				ctrlr.$setValidity('valuesEqualRqrd', ctrlr.$viewValue === otherModelValue);
			});

			ctrlr.$parsers.push(function(viewValue) {
				return validateEqual(viewValue, scope.$eval(attrs.valuesEqualRqrd));
			});

			ctrlr.$formatters.push(function(modelValue) {
				return validateEqual(modelValue, scope.$eval(attrs.valuesEqualRqrd));
			});
		}
	};
});