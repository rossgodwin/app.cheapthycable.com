define([], function() {
	return ctrlr;
	
	function ctrlr() {
		var startPage = 0;
		var currentPage = 0;
		var pages = [];
		var totalPages = 0;
		
		return {
			startPage : startPage,
			currentPage : currentPage,
			pages : pages,
			totalPages : totalPages,
			update : update
		};
		
		function update(totalItems, currentPage, pageSize) {
			this.currentPage = currentPage || 1;
			
			pageSize = pageSize || 10;
			
			this.totalPages = Math.ceil(totalItems / pageSize);
			
			var startPage, endPage;
			if (this.totalPages <= 10) {
				startPage = 1;
				endPage = this.totalPages;
			} else {
				if (currentPage <= 6) {
					startPage = 1;
					endPage = 10;
				} else if (currentPage + 4 >= this.totalPages) {
					startPage = this.totalPages - 9;
					endPage = this.totalPages;
				} else {
					startPage = currentPage - 5;
					endPage = currentPage + 4;
				}
			}
			
			this.startPage = (currentPage - 1) * pageSize;
			
			this.pages = [];
			for (var i = startPage; i <= endPage; i++) {
				this.pages.push(i);
			}
		}
	};
})