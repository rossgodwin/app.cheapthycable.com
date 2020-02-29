define(['src/app/res/AppConsts'], function(consts) {
	var resource = {
		getRelativeUrl : getRelativeUrl,
		getRestUrl : getRestUrl,
		getSrvUrl : getSrvUrl,
		getImgUrl : getImgUrl,
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
		return getRelativeUrl('/app/rest' + url);
	}
	
	function getSrvUrl(url) {
		return getRelativeUrl('/app/srv' + url);
	}
	
	function getImgUrl() {
		var url = '';
		if (consts.contextPath) {
			url += consts.contextPath + '/';
		}
		url += 'client/assets/img';
		return url;
	}
	
	function getLogoUrl() {
		return getImgUrl() + '/client_logo_rectangle_xs.png';
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