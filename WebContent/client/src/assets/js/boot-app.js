(function(head) {
	"use strict";

	head.js(
		{
			require : "client/vendor/requirejs/require-2.2.0.js"
		},
		{
			angular : "client/vendor/angular/angular-1.5.8.js"
		},
		{
			uiRouter : "client/vendor/angular/extras/angular-ui-router-0.3.1.min.js"
		},
		{
			ngMessages : "client/vendor/angular/extras/angular-messages-1.5.9.min.js"
		},
		{
			uiBootstrap : "client/vendor/angular/extras/ui-bootstrap-tpls-1.3.3.js"
		},
		{
			angularSpinners : "client/vendor/angular/extras/angular-spinners.min.js"
		},
		{
			ngCurrency : "client/vendor/angular/extras/ng-currency-0.8.2.min.js"
		},
		{
			uiSwitch : "client/vendor/jumplink/angular-toggle-switch.min.js"
		},
		{
			angularSanitize : "client/vendor/angular/extras/angular-sanitize.js"
		},
		{
			easyfb : "client/vendor/angular/extras/angular-easyfb.min.js"
		}
	).ready("ALL", function() {
		require.config({
			appDir : '',
			baseUrl : 'client/src',
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
				'uiBootstrap' : {
					deps : ['angular']
				},
				'angularSpinners' : {
					deps : ['angular']
				},
				'ngCurrency' : {
					deps : ['angular']
				},
				'uiSwitch' : {
					deps : ['angular']
				},
				'angularSanitize' : {
					deps : ['angular']
				},
				'easyfb' : {
					deps : ['angular']
				}
			}
		});

		require(["app/app-secure"], function(app) {
			// Application has bootstrapped and started...
		});
	});
}(window.head));