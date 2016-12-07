<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.gwn.xcbl.common.AppConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title><%= AppConstants.APP_NAME %></title>
	
	<%! String brandColorHex = "#ff3108"; %>
	<% boolean isLoggedIn = request.getUserPrincipal() != null; %>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="client/dist/assets/css/app-public.min.css" />
	
	<style type="text/css">
		.pg-cstm-navbar {
			height: 20px;
			background-color: white;
			padding: 0px;
			margin: 0px;
		}
		
		.pg-cstm-jumbotron {
			text-align: center;
			padding: 0px;
			margin: 0px;
			background-image: url("assets/img/web_jumbotron_background.png");
		}
		
		.pg-lead-text {
			border-radius: 30px;
			background-color: <%= brandColorHex %>;
			font-weight: bold;
			color: white;
			padding: 10px;
			font-size: 1.5em;
		}
		
		.pg-footer {
			background-color: #37454d;
			color: white;
			min-height: 150px;
		}
	</style>
</head>

<body>
	<nav class="navbar pg-cstm-navbar">
		<div class="container">
			<div class="navbar-right">
				<div style="padding: 5px;">
					<a style="text-decoration: none;" href="<%= AppConstants.TWITTER_HOME_PAGE_URL %>" target="_blank"><span class="icon-twitter cursor-pointer" style="color: <%= brandColorHex %>; font-size: 2em; margin-right: 5px;"></span></a>
					<a style="text-decoration: none;" href="<%= AppConstants.FB_HOME_PAGE_URL %>" target="_blank"><span class="icon-facebook cursor-pointer" style="color: <%= brandColorHex %>; font-size: 2em; margin-right: 5px;"></span></a>
					<% if (!isLoggedIn) { %>
						<a href="login" class="btn btn-brand-primary" role="button">Login</a>
					<% } else { %>
						<a href="app" class="btn btn-brand-primary" role="button">App</a>
					<% } %>
				</div>
			</div>
		</div>
	</nav>

	<div class="jumbotron pg-cstm-jumbotron" style="border-top: solid 1px <%= brandColorHex %>; border-bottom: solid 1px <%= brandColorHex %>">
		<div class="container">
			<img src="assets/img/web_logo_rectangle_lg.png" class="img-responsive"
				style="display: block; margin-left: auto; margin-right: auto;">

			<div style="color: <%= brandColorHex %>; font-size: 1.25em; font-weight: bold;">A community to help negotiate lower cable bills</div>

			<div style="text-align: center; margin-top: 10px; margin-bottom: 10px;">
				<a href="signup" class="btn btn-lg btn-success" role="button">Get Started for Free!</a>
			</div>
		</div>
	</div>

	<div class="container" style="padding-top: 10px; padding-bottom: 10px;">
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<span class="pg-lead-text">What</span><span style="font-size: 1.25em;">&nbsp;is this?</span>
				<p style="padding: 20px;">This is a place to gain information to
					allow you to negotiate a better rate from your cable/dish
					company. You will be able to see what people in your area are
					paying.</p>
				<hr>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<span class="pg-lead-text">How</span><span style="font-size: 1.25em;">&nbsp;does it work?</span>
				<p style="padding: 20px;">Sign up in just seconds. Take a quick
					survey about your internet, cable tv and/or phone bill. No personal
					information whatsoever is collected, just an email address, and we do not sell this information. Then
					you will have access to search what others in your zip code, city
					and state are paying for similar services. You are then armed with
					information for negotiating a lower cable bill.</p>
				<hr>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<span class="pg-lead-text">Why</span><span style="font-size: 1.25em;">&nbsp;do I need this?</span>
				<p style="padding: 20px;">We have built this site because we
					dislike spending money we don't have to. We also value transparency and dislike the way
					cable companies treat their customers. They are constantly raising
					rates and making you call them to lower it. We want to make this
					process easier for you.</p>
				<hr>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<span class="pg-lead-text">How</span><span style="font-size: 1.25em;">&nbsp;much
					does it cost?</span>
				<p style="padding: 20px;">This service is free.</p>
				<div style="text-align: center;">
					<a href="signup" class="btn btn-lg btn-success" role="button">Get Started</a>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>

	<!--<footer class="pg-footer">
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				On this site:
				<br /><br />
				Home
				<br />
				FAQ
				<br />
				Login
			</div>
			<div class="col-md-3"></div>
		</div>
	</footer>-->

	<script type="text/javascript" async src="https://platform.twitter.com/widgets.js"></script>
</body>
</html>