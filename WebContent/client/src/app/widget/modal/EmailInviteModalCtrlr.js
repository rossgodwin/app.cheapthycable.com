define([
	'src/app/utils/ArrayUtils'
], function(ArrayUtils) {
	return ['$uibModalInstance', ctrlr];
	
	function ctrlr($uibModalInstance) {
		var vm = this;
		
		// public variables
		vm.emails = [];
		vm.errs = [];
		
		// public functions
		vm.addEmail = addEmail;
		vm.removeEmail = removeEmail;
		vm.send = send;
		vm.cancel = cancel;
		
		function addEmail(modelCtrlr) {
			vm.errs = [];
			if (modelCtrlr.$valid) {
				var email = modelCtrlr.$modelValue;
				if (!ArrayUtils.contains.call(vm.emails, email)) {
					vm.emails.push(email);
				} else {
					vm.errs.push('That email address already exist');
				}
				vm.newEmail = '';
			} else {
				vm.errs.push('That is not a valid email address');
			}
		}
		
		function removeEmail(email) {
			var index = vm.emails.indexOf(email);
			if (index > -1) {
				vm.emails.splice(index, 1);
			}
		}
		
		function send() {
			vm.errs = [];
			if (vm.emails.length > 0) {
				$uibModalInstance.close();
			} else {
				vm.errs.push('You haven\'t entered any emails to send invites');
			}
		}
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	};
});