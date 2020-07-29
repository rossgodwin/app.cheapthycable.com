<%@ page import="com.gwn.xcbl.common.AppConstants" %>
<%@ page import="com.gwn.xcbl.data.model.AppData" %>
<%@ page import="com.gwn.xcbl.data.shared.UserConstants" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%= request.getContextPath() %>/">
	
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	
	<!-- @if NODE_ENV == 'DEVELOPMENT' -->
	<link rel="stylesheet/less" type="text/css" href="client/assets/less/app-main.less" />
	<!-- @endif -->
	<!-- @if NODE_ENV == 'PRODUCTION' -->
	<link rel="stylesheet" type="text/css" href="client/dist/assets/css/app-public.min.css" />
	<!-- @endif -->
</head>
<body>
	<div ui-view></div>
	
	<!-- @if NODE_ENV == 'DEVELOPMENT' -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/less.js/2.6.1/less.min.js"></script>
	<script src="client/vendor/headjs/head-1.0.2.js" type="text/javascript"></script>
	<script src="client/src/assets/js/boot-public.js" type="text/javascript"></script>
	<!-- @endif -->
	<!-- @if NODE_ENV == 'PRODUCTION' -->
	<script src="client/dist/assets/js/head.js" type="text/javascript"></script>
	<script src="client/dist/assets/js/boot-public.js" type="text/javascript"></script>
	<!-- @endif -->
	
	<script type="text/javascript">
		<%
			int pwdMinLength = UserConstants.PWD_MIN_LENGTH;
			String appName = AppConstants.APP_NAME;
			String appUrl = AppData.getInstance().getDomainUrl();
			String recaptchaSiteKey = AppData.getInstance().getRecaptchaSiteKey();
		%>
		var pwdMinLength = <%= pwdMinLength %>;
		var appName = '<%= appName %>';
		var appUrl = '<%= appUrl %>';
		var recaptchaSiteKey = '<%= recaptchaSiteKey %>';
	</script>
</body>
</html>