define([], function() {
	return [prvdr];

	function prvdr() {
		this.$get = model;
		
		function model() {
			/*
			 * https://github.com/angular/angular.js/issues/1011
			 * http://stackoverflow.com/questions/34642647/angular-resetting-factory-or-service
			 */
			var model = {};
			var defaultModel = {
				zipCode : '',
				geoZipCode : null,
				selectedProvider : null,
				typedProviderName : '',
				lookupProviderName : '',
				totalAmount : '',
				internetService : false,
				cableService : false,
				phoneService : false,
				internetOptions : {
					modem : true,
//					downloadSpeedMbps : 10,
					downloadSpeedMbpsRngLower : 6,
					downloadSpeedMbpsRngUpper : 10
				},
				cableOptions : {
					dvrCount : 0,
					boxCount : 1,
					specialChannels : false
				},
				getServices : getServices,
				getProviderName : getProviderName,
				reset : reset
			};
			
			init();

			return model;

			function init() {
				defaultModel.reset();
				geoZipCode = null;
			}
			
//			return function() {
//				defaultModel.reset();
//				geoZipCode = null;
//				return model;
//			};
			
			function getServices() {
				var r = [];
				if (model.internetService) {
					r.push('Internet');
				}
				if (model.cableService) {
					r.push('Cable');
				}
				if (model.phoneService) {
					r.push('Phone');
				}
				return r;
			}
			
			function getProviderName() {
				if (model.lookupProviderName) {
					return model.lookupProviderName;
				} else if (model.selectedProvider) {
					return model.selectedProvider.name;
				} else {
					return model.typedProviderName;
				}
			}
			
			function reset() {
				return model = angular.extend(model, defaultModel);
			}
		}
	}
});