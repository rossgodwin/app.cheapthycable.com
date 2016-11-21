define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$window', '$stateParams', '$timeout', '$filter', 'commentBill', 'spinnerService', 'DsqBillCommentHttpService', ctrlr];
	
	function ctrlr($window, $stateParams, $timeout, $filter, commentBill, spinnerService, DsqBillCommentHttpService) {
		var vm = this;
		
		// public variables
		vm.titlebarText = 'Bill Comments';
		vm.disqusConfig = {
			    disqus_shortname : $window.disqusShortname,
			    disqus_identifier : commentBill.id,
//			    disqus_title : getDisqusTitle(),
			    disqus_title : 'Bill (' + $stateParams.encBillId + ') Comments',
			    disqus_url : $window.location.href,
			    disqus_onNewCommentCb : onNewCommentCbHndlr
		};
		vm.bill = commentBill;
		vm.newCommentSuccessVisible = false;
		
		// public functions
		vm.titlebarBack = back;
		
		init();
		
		function init() {
		}
		
		function back() {
			$window.close();
		}
		
		function getDisqusTitle() {
			var amt = $filter('currency')(commentBill.totalAmount);
			var location = commentBill.geoZipCode.city + ', ' + commentBill.geoZipCode.stateCode + ' ' + commentBill.geoZipCode.zipCode;
			var provider = commentBill.provider ? commentBill.provider.name : commentBill.providerOther;
			var r = 'Bill Amount: ' + amt + ' | Location: ' + location + ' | ' + provider;
			console.log('disqus title: ' + r);
			return r;
		}
		
		/**
		 * https://help.disqus.com/customer/portal/articles/466258-capturing-disqus-commenting-activity-via-callbacks
		 */
		function onNewCommentCbHndlr(comment) {
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			DsqBillCommentHttpService.createComment(vm.bill.id, comment.id).then(function(response) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				showNewCommentSuccessAlert();
			}).catch(function(error) {
				// TODO
			});
		}
		
		function showNewCommentSuccessAlert() {
			vm.newCommentSuccessVisible = true;
			
			$timeout(function() {
				vm.newCommentSuccessVisible = false;
			}, 13000);
		}
	};
});