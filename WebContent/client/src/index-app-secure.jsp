<%@ page import="com.gwn.xcbl.common.AppConstants" %>
<%@ page import="com.gwn.xcbl.data.model.AppData" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%= request.getContextPath() %>/">
	
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	
	<link rel="stylesheet/less" type="text/css" href="client/assets/less/app-main.less" />
	<link rel="stylesheet/less" type="text/css" href="client/vendor/jumplink/angular-toggle-switch-bootstrap-3.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/less.js/2.6.1/less.min.js"></script>
	
	<script type="text/javascript">
		<%
			String appName = AppConstants.APP_NAME;
			String appUrl = AppData.getInstance().getDomainUrl();
			String fbAppId = AppData.getInstance().getFbAppId();
			String fbHomePageUrl = AppConstants.FB_HOME_PAGE_URL;
			String twitterHomePageUrl = AppConstants.TWITTER_HOME_PAGE_URL;
			String twitterIntentUrl = AppConstants.TWITTER_INTENT_URL;
			String twitterScreenName = AppConstants.TWITTER_SCREEN_NAME;
			String disqusShortname = AppData.getInstance().getDisqusShortname();
		%>
		var appName = '<%= appName %>';
		var appUrl = '<%= appUrl %>';
		var fbAppId = '<%= fbAppId %>';
		var fbHomePageUrl = '<%= fbHomePageUrl %>';
		var twitterHomePageUrl = '<%= twitterHomePageUrl %>';
		var twitterIntentUrl = '<%= twitterIntentUrl %>';
		var twitterScreenName = '<%= twitterScreenName %>';
		var disqusShortname = '<%= disqusShortname %>';
	</script>
</head>
<body>
	<div id="app-splash" class="app-splash" ng-animate-children>
		<style type="text/css">
			div.app-splash {
				color: #FFFFFF;
				position: absolute;
				top: 50%;
				transform: translate(0, -50%);
				left: 0;
				right: 0;
			}
		</style>
		<img src="client/assets/img/client_logo_splash.png" class="img-responsive" style="display: block; margin-left: auto; margin-right: auto;">
	</div>
	
	<div ui-view></div>
	
	<script src="client/vendor/headjs/head-1.0.2.js" type="text/javascript"></script>
	<script src="client/src/assets/js/boot-secure.js" type="text/javascript"></script>
	<script type="text/javascript" async src="https://platform.twitter.com/widgets.js"></script>
</body>
</html>