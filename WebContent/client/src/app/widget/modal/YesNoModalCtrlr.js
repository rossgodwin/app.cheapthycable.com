define([], function() {
	return ['$uibModalInstance', 'title', 'message', 'yesButtonText', 'noButtonText', ctrlr];
	
	function ctrlr($uibModalInstance, title, message, yesButtonText, noButtonText) {

		var vm = this;
		
		vm.title = title;
		vm.message = message;
		vm.yesButtonText = yesButtonText;
		vm.noButtonText = noButtonText;
		vm.yes = yes;
		vm.no = no;
		
		function yes() {
			$uibModalInstance.close();
		}
		
		function no() {
			$uibModalInstance.dismiss('cancel');
		}
	};
});