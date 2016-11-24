define(['src/app/res/AppConsts'], function(appConsts) {
	return ['$window', '$stateParams', '$timeout', '$filter', 'commentBill', 'spinnerService', 'DsqBillPostHttpService', ctrlr];
	
	function ctrlr($window, $stateParams, $timeout, $filter, commentBill, spinnerService, DsqBillPostHttpService) {
		var vm = this;
		var encBillId = $stateParams.encBillId;
		var disqusTitle = 'Bill Comments (id: ' + encBillId + ')';
		var disqusUrl = $window.location.href;
		
		// public variables
		vm.titlebarText = 'Bill Comments';
		vm.disqusConfig = {
			    disqus_shortname : $window.disqusShortname,
			    disqus_identifier : commentBill.id,
			    disqus_title : disqusTitle,
			    disqus_url : disqusUrl,
			    disqus_onNewCommentCb : onNewPostCbHndlr
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
		function onNewPostCbHndlr(post) {
			spinnerService.show(appConsts.screenLoadingSpinner);
			
			DsqBillPostHttpService.createPost(vm.bill.id, post.id).then(function(response) {
				spinnerService.hide(appConsts.screenLoadingSpinner);
				
				showNewPostSuccessAlert();
			}).catch(function(error) {
				// TODO
			});
		}
		
		function showNewPostSuccessAlert() {
			vm.newCommentSuccessVisible = true;
			
			$timeout(function() {
				vm.newCommentSuccessVisible = false;
			}, 13000);
		}
	};
});