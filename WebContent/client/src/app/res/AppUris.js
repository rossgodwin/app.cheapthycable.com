define(['app/res/AppConsts'], function(consts) {
	var resource = {
		getRelativeUrl : getRelativeUrl,
		getRestUrl : getRestUrl,
		getSrvUrl : getSrvUrl,
		getImgsUrl : getImgsUrl,
		getLogoUrl : getLogoUrl,
		getHomeUrl : getHomeUrl,
		getLoginUrl : getLoginUrl,
		getAppUrl : getAppUrl
	};
	
	return resource;
	
	function getRelativeUrl(url) {
		return consts.contextPath + url;
	}
	
	function getRestUrl(url) {
		return consts.contextPath + '/rest' + url;
	}
	
	function getSrvUrl(url) {
		return consts.contextPath + '/srv' + url;
	}
	
	function getImgsUrl() {
		return consts.contextPath + '/assets/images';
	}
	
	function getLogoUrl() {
		return getImgsUrl() + '/logo.png';
	}
	
	function getHomeUrl() {
		return getRelativeUrl('/');
	}
	
	function getLoginUrl() {
		return getRelativeUrl('/login');
	}
	
	function getAppUrl() {
		return getRelativeUrl('/app');
	}
});