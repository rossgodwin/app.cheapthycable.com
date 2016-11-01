define(['app/res/AppUris'], function(appUris) {
	return ['$http', srvc];
	
	function srvc($http) {
		return {
			isSignupUniqueEmail : isSignupUniqueEmail,
			isPwdValidChk : isPwdValidChk,
			signup : signup,
			qsignup : qsignup,
			login : login,
			getUser : getUser,
			pwdForgot : pwdForgot,
			pwdReset : pwdReset,
			logout : logout
		};
		
		function isSignupUniqueEmail(email) {
			return $http({
				method : 'POST',
			    url : appUris.getRestUrl('/auth/signup/uniqueEmail'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			    	var str = [];
			    	for (var p in obj) {
			    		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			    	}
			    	return str.join("&");
			    },
			    data : {
			    	p0: email
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
		
		function isPwdValidChk(pwd) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/pwd/chk'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	pwd : pwd
			    }
			});
		}
		
		function signup(signup) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/signup'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			    	var str = [];
			    	for (var p in obj) {
			    		str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			    	}
			    	return str.join("&");
			    },
			    data : {
			    	p0: JSON.stringify(signup)
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
		
		function qsignup(signup) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/qsignup'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	p0: JSON.stringify(signup)
			    }
			});
		}
		
		function login(email, password) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/login'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	username : email,
			    	password : password
			    }
			});
		}
		
		function getUser() {
			return $http.get(appUris.getRestUrl('/auth/user'), {
				params : {
				}
			});
		}
		
		function pwdForgot(email) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/pwd/forgot'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	email : email
			    }
			});
		}
		
		function pwdReset(token, newPwd) {
			return $http({
			    method : 'POST',
			    url : appUris.getRestUrl('/auth/pwd/reset'),
			    headers : {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest : function(obj) {
			        var str = [];
			        for (var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        }
			        return str.join("&");
			    },
			    data : {
			    	token : token,
			    	newPwd : newPwd
			    }
			});
		}
		
		function logout() {
			return $http.post(appUris.getRestUrl('/auth/logout')).then(function(result) {
			});
		}
	};
});