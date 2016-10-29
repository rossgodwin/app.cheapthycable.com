define([
	'app/res/AppUris'
], function(uris) {
	var service = function($http) {
		return {
			getMyBillsList : getMyBillsList,
			getBillsListPage : getBillsListPage,
			saveBill : saveBill,
			getLatestBill : getLatestBill,
			deleteBill : deleteBill,
			getBillExplorerResults : getBillExplorerResults,
			getBillsByTotalAmount : getBillsByTotalAmount,
			getBillsByTotalAmount2 : getBillsByTotalAmount2
		};
		
		function getMyBillsList() {
			return $http.get(uris.getRestUrl('/bill/my/list'), {
				params : {
				}
			}).then(c)
			.catch(f);
			
			function c(response) {
				return response.data.result;
			}
			
			function f(error) {
//					$location.url(relativeUrl(''));
			}
		}
		
		function getBillsListPage(criteria, offset, limit) {
			return $http.get(uris.getRestUrl('/bill/list/page'), {
				params : {
					p0 : JSON.stringify(criteria),
					p1 : offset,
					p2 : limit
				}
			})
		}
		
		function saveBill(obj) {
			return $http({
			    method : 'POST',
			    url : uris.getRestUrl('/bill/save'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : JSON.stringify(obj)
			    }
			});
		}
		
		function getLatestBill() {
			return $http.get(uris.getRestUrl('/bill/latest'), {
				params : {
				}
			});
		}
		
		function deleteBill(billId) {
			return $http({
			    method : 'POST',
			    url : uris.getRestUrl('/bill/' + billId + '/delete'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    }
//				    data : {p0 : JSON.stringify(obj)}
			}).then(c)
			.catch(f);
			
			function c(response) {
				return response.data;
			}
			
			function f(error) {
				// TODO
			}
		}
		
		function getBillExplorerResults(zipCode, radius) {
			return $http.get(uris.getRestUrl('/bill/explorer/results'), {
				params : {
					p0 : zipCode,
			    	p1 : radius
				}
			});
			
//			return $http({
//			    method : 'POST',
//			    url : uris.getRestUrl('/bill/explorer/results'),
//			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
//			    transformRequest : function(obj) {
//			        var str = [];
//			        for (var p in obj) {
//			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
//			        }
//			        return str.join("&");
//			    },
//			    data : {
//			    	p0 : zipCode,
//			    	p1 : radius
//			    }
//			}).then(c)
//			.catch(f);
//			
//			function c(response) {
//				return response.data.result;
//			}
//			
//			function f(error) {
//				// TODO
//			}
		}
		
		function getBillsByTotalAmount(zipCode, radius, totalAmount) {
			return $http({
			    method : 'POST',
			    url : uris.getRestUrl('/bill/report/billsByTotalAmount'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : zipCode,
			    	p1 : radius,
			    	p2 : totalAmount
			    }
			}).then(c)
			.catch(f);
			
			function c(response) {
				return response.data.result;
			}
			
			function f(error) {
				// TODO
			}
		}
		
		function getBillsByTotalAmount2(zipCode, radius, totalAmount) {
			return $http({
			    method : 'POST',
			    url : uris.getRestUrl('/bill/report/billsByTotalAmount'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : zipCode,
			    	p1 : radius,
			    	p2 : totalAmount
			    }
			});
		}
	};
	
	return ['$http', service];
});