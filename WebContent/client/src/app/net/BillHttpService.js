define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			getMyBillsList : getMyBillsList,
			getBillsListPage : getBillsListPage,
			saveBill : saveBill,
			getLatestBill : getLatestBill,
			getBillDetail : getBillDetail,
			deleteBill : deleteBill,
			getBillExplorerStats : getBillExplorerStats
		};
		
		function getMyBillsList() {
			return $http.get(appUris.getRestUrl('/bill/my/list'), {
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
			return $http.get(appUris.getRestUrl('/bill/list/page'), {
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
			    url : appUris.getRestUrl('/bill/save'),
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
			return $http.get(appUris.getRestUrl('/bill/current'), {
				params : {
				}
			});
		}
		
		function getBillDetail(billId) {
			return $http.get(appUris.getRestUrl('/bill/' + billId + '/detail'), {
				params : {
				}
			});
		}
		
		function deleteBill(billId) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/bill/' + billId + '/delete'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    }
			}).then(c)
			.catch(f);
			
			function c(response) {
				return response.data;
			}
			
			function f(error) {
				// TODO
			}
		}
		
//		function getBillExplorerResults(zipCode, radius) {
//			return $http.get(appUris.getRestUrl('/bill/explorer/results'), {
//				params : {
//					p0 : zipCode,
//			    	p1 : radius
//				}
//			});
//		}
		function getBillExplorerStats(criteria) {
			return $http.get(appUris.getRestUrl('/bill/explorer/stats'), {
				params : {
					p0 : JSON.stringify(criteria)
				}
			})
		}
	};
});