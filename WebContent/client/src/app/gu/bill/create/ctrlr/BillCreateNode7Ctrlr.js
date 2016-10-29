define([], function() {
	return ['$state', 'billCreateNavHlpr', 'fbHlprPrvdr', 'twitterHlprPrvdr', ctrlr];
	
	function ctrlr($state, navHlpr, fbHlprPrvdr, twitterHlprPrvdr) {
		var vm = this;
		
		vm.tweet = tweet;
		vm.fbShare = fbShare;
		vm.finish = finish;
		
		init();
		
		function init() {
		}
		
		function tweet() {
			return twitterHlprPrvdr.tweetUrlViaAppRefWebsite('I searched what others in my area are paying for cable, internet and phone for FREE!');
		}
		
		function fbShare() {
			fbHlprPrvdr.promote('I signed up for this FREE website that allows me to see what others are paying for cable/satellite. Give it a try!');
		}
		
		function finish() {
			$state.go(navHlpr.getOnFinishState(), {}, {location : false});
		}
	};
});