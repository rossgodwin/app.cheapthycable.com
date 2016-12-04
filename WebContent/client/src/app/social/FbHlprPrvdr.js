define(['src/app/res/AppUris'], function(appUris) {
	return ['$window', 'ezfb', 'locationSrvc', srvc];
	
	function srvc($window, ezfb, locationSrvc) {
		var loginStatus;
		var apiMe;

		return {
			login : login,
			share : share,
			follow : follow,
			promote : promote
		};
		
		function getPictureUrl() {
			var url = locationSrvc.getBaseUrl() + appUris.getImgUrl() + '/client_logo_facebook.png';
			return url;
		}
		
		function login() {
			ezfb.login(function(res) {
				/**
				 * no manual $scope.$apply, I got that handled
				 */
				if (res.authResponse) {
					updateLoginStatus(updateApiMe);
				}
			}, {
				scope : 'email,user_likes'
			});
		}

		function share(params) {
			ezfb.ui(params, function(res) {
				// res: FB.ui response
			});
		}

		function updateLoginStatus(more) {
			ezfb.getLoginStatus(function(res) {
				loginStatus = res;

				(more || angular.noop)();
			});
		}

		function updateApiMe() {
			ezfb.api('/me', function(res) {
				apiMe = res;
			});
		}
		
		function promote(description) {
			ezfb.ui({
				method : 'feed',
				picture : getPictureUrl(),
		        name : 'Learn what others are paying for cable/satellite service and lower your bill',
		        link : $window.appUrl,
		        description : description,
		        caption : ''
			}, function(res) {
				// res: FB.ui response
			});
		}
		
		function follow() {
			ezfb.ui({
				method : 'friends.add',
				id : $window.fbAppId
			}, function(res) {
				// res: FB.ui response
			});
			
//			ezfb.api(
//				'/me/og.follows',
//			    'POST',
//			    {
//			        "profile": "http:\/\/samples.ogp.me\/390580850990722",
//			        "object": "{\"fb:app_id\":\"302184056577324\"}"
//			    },
//			    function (response) {
//			    	console.log(response);
//			    }
//			)
			
//			FB.api('/me/feed', 'post', { message: body }, function(response) {
//				  if (!response || response.error) {
//				    alert('Error occured');
//				  } else {
//				    alert('Post ID: ' + response.id);
//				  }
//				})
			
			
//			FacebookService.share({
//				method: 'feed',
//				picture : uris.getImgLogo400x400Url(),
//		        name: 'Lower your cable bill',
//		        link: consts.websiteUrl,
//		        description: 'I learned what others are paying for cable!',
//		        caption : ''
//			});
		}
		
//		function facebookLike() {
//		FacebookService.login();
//		FacebookService.share({
//			method: 'feed',
//			picture : uris.getImgLogo400x400Url(),
//	        name: 'Lower your cable bill',
//	        link: consts.websiteUrl,
//	        description: 'I learned what others are paying for cable!',
//	        caption : ''
//		});
		
//		ezfb.ui(
//			      {
//			        method: 'feed',
//			        name: 'angular-easyfb API demo',
//			        picture: 'http://plnkr.co/img/plunker.png',
//			        link: 'http://plnkr.co/edit/qclqht?p=preview',
//			        description: 'angular-easyfb is an AngularJS module wrapping Facebook SDK.' + 
//			                     ' Facebook integration in AngularJS made easy!' + 
//			                     ' Please try it and feel free to give feedbacks.'
//			      },
//			      function (res) {
//			        // res: FB.ui response
//			      }
//			    );
		
//		FB.api('/me/feed', 'post', {
//		      message:'my_message',
//		      link:YOUR_SITE_URL,
//		      picture:picture_url
//		      name: 'Post name',
//		      description: 'description'
//		 },function(data) {
//		      console.log(data);
//		 });
//	}
		
		
	};
});