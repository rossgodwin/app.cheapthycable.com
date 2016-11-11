define([], function() {
	return [prvdr];

	function prvdr() {
		this.$get = model;
		
		function model() {
			var model = {};
			var defaultModel = {
				receiveEmail : true,
				receiveEmailFrequencyDays : 14,
				critrMileRadius : 75,
				critrAmountBelow : 50,
				reset : reset
			};
			
			init();

			return model;

			function init() {
				defaultModel.reset();
			}
			
			function reset() {
				return model = angular.extend(model, defaultModel);
			}
		}
	}
});