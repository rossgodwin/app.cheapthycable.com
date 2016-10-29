(function(head) {
	"use strict";

	head.js(
		{
			app : "client/dist/app-public.js"
		}
	).ready("ALL", function() {
		require(["app/login-main"], function(app) {
		});
	});
}(window.head));