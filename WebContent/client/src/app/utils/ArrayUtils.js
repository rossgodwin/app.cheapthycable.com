define([], function() {
	return {
		contains : contains
	}

	/**
	 * http://stackoverflow.com/questions/1181575/determine-whether-an-array-contains-a-value
	 * usage: ArrayUtils.contains.call([], valueToLookup)
	 */
	function contains(needle) {
		// Per spec, the way to identify NaN is that it is not equal to itself
		var findNaN = needle !== needle;
		var indexOf;

		if (!findNaN && typeof Array.prototype.indexOf === 'function') {
			indexOf = Array.prototype.indexOf;
		} else {
			indexOf = function(needle) {
				var i = -1, index = -1;

				for (i = 0; i < this.length; i++) {
					var item = this[i];

					if ((findNaN && item !== item) || item === needle) {
						index = i;
						break;
					}
				}

				return index;
			};
		}

		return indexOf.call(this, needle) > -1;
	};
	
	// below is implementation of indexOf if you need it
//	https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/indexOf#Polyfill
//	Array.prototype.indexOf || (Array.prototype.indexOf = function(d, e) {
//	    var a;
//	    if (null == this) throw new TypeError('"this" is null or not defined');
//	    var c = Object(this),
//	        b = c.length >>> 0;
//	    if (0 === b) return -1;
//	    a = +e || 0;
//	    Infinity === Math.abs(a) && (a = 0);
//	    if (a >= b) return -1;
//	    for (a = Math.max(0 <= a ? a : b - Math.abs(a), 0); a < b;) {
//	        if (a in c && c[a] === d) return a;
//	        a++
//	    }
//	    return -1
//	});
});