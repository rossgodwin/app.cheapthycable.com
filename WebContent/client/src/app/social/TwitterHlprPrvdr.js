define([], function() {
	return ['$window', srvc];
	
	function srvc($window) {
		var intentUrl = $window.twitterIntentUrl;
		var screenName = $window.twitterScreenName;
		
		return {
			follow : follow,
			tweetUrlViaAppRefWebsite : tweetUrlViaAppRefWebsite,
			tweetUrlViaApp : tweetUrlViaApp
		};
		
		function follow() {
			var tweetUrl = intentUrl.concat('/follow?', 'screen_name=' + screenName);
			return tweetUrl;
		}
		
		function tweetUrlViaAppRefWebsite(text) {
			return tweetUrlViaApp(text, $window.appUrl);
		}
		
		function tweetUrlViaApp(text, url) {
			var params = [];
			if (text) {
				params.push('text=' + encodeURIComponent(text));
			}
			if (url) {
				params.push('url=' + encodeURIComponent(url));
			}
			params.push('via=' + screenName);
			var tweetUrl = intentUrl.concat('/tweet?', params.join('&'));
			return tweetUrl;
		}
	};
});