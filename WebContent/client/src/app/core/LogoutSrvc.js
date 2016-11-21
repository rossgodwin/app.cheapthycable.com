define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$uibModal', 'AuthHttpService', 'principal', srvc];
	
	function srvc($window, $uibModal, AuthHttpService, principal) {
		return {
			logout : logout
		};
		
		function logout() {
			var modal = $uibModal.open({
				templateUrl : 'client/src/app/widget/modal/yes-no.modal.tpl.html',
				controller : 'YesNoModalCtrlr',
				controllerAs : "ctrlr",
				size : 'sm',
				resolve : {
					title : function() {
						return "Confirm Logout";
					},
					message : function() {
						return "Are you sure you want to logout?";
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
				AuthHttpService.logout().then(function(result) {
					principal.authenticate(null);
					$window.location.href = appUris.getLoginUrl();
				});
			});
		}
	}
})