(function(head) {
	"use strict";

	head.js(
		{
			require : "client/vendor/requirejs/require-2.2.0.js"
		},
		{
			angular : "client/vendor/angular/angular-1.5.8.min.js"
		},
		{
			uiRouter : "client/vendor/angular/extras/angular-ui-router-0.3.1.min.js"
		},
		{
			ngMessages : "client/vendor/angular/extras/angular-messages-1.5.9.min.js"
		},
		{
			angularSpinners : "client/vendor/angular/extras/angular-spinners.min.js"
		},
		{
			angularSanitize : "client/vendor/angular/extras/angular-sanitize.js"
		}
	).ready("ALL", function() {
		require.config({
			appDir : '',
			baseUrl : 'client',
			priority : 'angular',
			shim : {
				'angular' : {
					exports : 'angular'
				},
				'uiRouter' : {
					deps : ['angular']
				},
				'ngMessages' : {
					deps : ['angular']
				},
				'angularSpinners' : {
					deps : ['angular']
				},
				'angularSanitize' : {
					deps : ['angular']
				}
			}
		});

		require(["src/app/app-public"], function(app) {
			// Application has bootstrapped and started...
		});
	});
}(window.head));