(function(head) {
	"use strict";

	head.js(
		{
			app : "client/dist/app-secure.js"
		}
	).ready("ALL", function() {
		require(["src/app/app-secure"], function(app) {
		});
	});
}(window.head));