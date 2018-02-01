(function(head) {
	"use strict";

	head.js(
		{
			app : "client/dist/app-public.js"
		}
	).ready("ALL", function() {
		require(["src/app/app-public"], function(app) {
		});
	});
}(window.head));