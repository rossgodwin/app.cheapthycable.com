define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', '$http', srvc];
	
	function srvc($window, $http) {
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
			}).then(function successCallback(response) {
				return response.data.result;
			}, errorCallback);
		}
		
		function getBillsListPage(criteria, offset, limit) {
			return $http.get(appUris.getRestUrl('/bill/list/page'), {
				params : {
					p0 : JSON.stringify(criteria),
					p1 : offset,
					p2 : limit
				}
			}).catch(errorCallback);
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
			}).catch(errorCallback);
		}
		
		function getLatestBill() {
			return $http.get(appUris.getRestUrl('/bill/current'), {
				params : {
				}
			}).catch(errorCallback);
		}
		
		function getBillDetail(billId) {
			return $http.get(appUris.getRestUrl('/bill/' + billId + '/detail'), {
				params : {
				}
			}).catch(errorCallback);
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
			}).then(function successCallback(response) {
				return response.data;
			}, errorCallback);
		}
		
		function getBillExplorerStats(criteria) {
			return $http.get(appUris.getRestUrl('/bill/explorer/stats'), {
				params : {
					p0 : JSON.stringify(criteria)
				}
			}).catch(errorCallback);
		}
		
		function errorCallback(response) {
			if (response.status == 401) {
				$window.location.href = appUris.getLoginUrl();
			}
		}
	};
});