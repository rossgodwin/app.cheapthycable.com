define([
	'src/app/gu/bill/create/BillCreateDrctv',
	'src/app/gu/bill/create/BillCreateModelPrvdr',
	'src/app/gu/bill/create/BillCreateNavHlprPrvdr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode0Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode1Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode2Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode3Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode4Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode5Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode6Ctrlr',
	'src/app/gu/bill/create/ctrlr/BillCreateNode7Ctrlr'
], function(
	BillCreateDrctv,
	BillCreateModelPrvdr,
	BillCreateNavHlprPrvdr,
	BillCreateNode0Ctrlr,
	BillCreateNode1Ctrlr,
	BillCreateNode2Ctrlr,
	BillCreateNode3Ctrlr,
	BillCreateNode4Ctrlr,
	BillCreateNode5Ctrlr,
	BillCreateNode6Ctrlr,
	BillCreateNode7Ctrlr
) {
	var module = angular.module('app.gu.bill.create', ['toggle-switch']);
	
	module.directive('guBillCreate', BillCreateDrctv);
	
	module.provider('billCreateModel', BillCreateModelPrvdr);
	module.provider('billCreateNavHlpr', BillCreateNavHlprPrvdr);
	
	module.controller('BillCreateNode0Ctrlr', BillCreateNode0Ctrlr);
	module.controller('BillCreateNode1Ctrlr', BillCreateNode1Ctrlr);
	module.controller('BillCreateNode2Ctrlr', BillCreateNode2Ctrlr);
	module.controller('BillCreateNode3Ctrlr', BillCreateNode3Ctrlr);
	module.controller('BillCreateNode4Ctrlr', BillCreateNode4Ctrlr);
	module.controller('BillCreateNode5Ctrlr', BillCreateNode5Ctrlr);
	module.controller('BillCreateNode6Ctrlr', BillCreateNode6Ctrlr);
	module.controller('BillCreateNode7Ctrlr', BillCreateNode7Ctrlr);
	
	return module;
});