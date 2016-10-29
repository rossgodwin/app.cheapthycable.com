define([], function() {
	var model = {};
	var defaultModel = {
		email : '',
		password : '',
		passwordConfirm : '',
		reset : reset
	}
	
	return function() {
		defaultModel.reset();
		return model;
	};
	
	function reset() {
		return model = angular.extend(model, defaultModel);
	}
})