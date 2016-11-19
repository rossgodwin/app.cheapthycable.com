define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			createComment : createComment
		};
		
		function createComment(billId, dsqCommentId) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/dsqBillComment/create'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0 : billId,
			    	p1 : dsqCommentId
			    }
			})
		}
	};
});