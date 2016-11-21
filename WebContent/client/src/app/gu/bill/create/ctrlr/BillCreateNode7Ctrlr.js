define(['src/app/res/AppConsts'], function(appConsts) {
	return ['receiveBillAlerts', '$state', 'fbHlprPrvdr', 'twitterHlprPrvdr', 'spinnerService', 'billCreateNavHlpr', 'principal', 'BaAlertHttpService', '$uibModal', ctrlr];
	
	function ctrlr(receiveBillAlerts, $state, fbHlprPrvdr, twitterHlprPrvdr, spinnerService, navHlpr, principal, BaAlertHttpService, $uibModal) {
		var vm = this;
		
		// public variables
		vm.serverErrs = [];
		vm.ask = !receiveBillAlerts;
		
		// public functions
		vm.tweet = tweet;
		vm.fbShare = fbShare;
		vm.getCssClasses = getCssClasses;
		vm.yesToAlerts = yesToAlerts;
		vm.noToAlerts = noToAlerts;
		vm.finish = finish;
		
		init();
		
		function init() {
			vm.email = principal.getUserEmail();
		}
		
		function tweet() {
			return twitterHlprPrvdr.tweetUrlViaAppRefWebsite('I searched what others in my area are paying for cable, internet and phone for FREE!');
		}
		
		function fbShare() {
			fbHlprPrvdr.promote('I signed up for this FREE website that allows me to see what others are paying for cable/satellite. Give it a try!');
		}
		
		function getCssClasses(formCtrlr, modelCtrlr) {
			return {
		    	'has-error': modelCtrlr.$invalid && (modelCtrlr.$dirty || formCtrlr.$submitted),
		    	'has-success': modelCtrlr.$valid
		    };
		};
		
		function yesToAlerts(formCtrlr) {
			console.log('formcontroller: ' + formCtrlr);
			
			vm.serverErrs.length = 0;
			
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			BaAlertHttpService.addDefaultAlert(vm.email).then(function(response) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				if (response.data.resultCode === 1) {
		    		finish();
		    	} else {
		    		var errs = response.data.errs;
					for (var i = 0; i < errs.length; i++) {
						vm.serverErrs.push(errs[i]);
					}
		    	}
			}).catch(function(error) {
				// TODO
			});
		}
		
		function noToAlerts() {
			var modal = $uibModal.open({
				templateUrl : 'client/src/app/widget/modal/yes-no.modal.tpl.html',
				controller : 'YesNoModalCtrlr',
				controllerAs : "ctrlr",
				size : 'sm',
				resolve : {
					title : function() {
						return "Confirm";
					},
					message : function() {
						return "Are you sure you do not want to receive bill alerts? This is a convenient, passive way for you to learn when bills are dropping.";
					},
					yesButtonText : function() {
						return "Yes";
					},
					noButtonText : function() {
						return "No";
					}
				}
			});
			
			modal.result.then(function() {
				finish();
			});
		}
		
		function finish() {
			$state.go(navHlpr.getOnFinishState(), {}, {location : false});
		}
	};
});