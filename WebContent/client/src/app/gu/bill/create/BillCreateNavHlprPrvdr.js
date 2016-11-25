define([], function() {
	return [prvdr];

	function prvdr() {
		this.$get = hlpr;
		
		hlpr.$inject = ['$state'];
		
		function hlpr($state) {
			var baseState;
			var onFinishState;
//			var progressMax = 7;
			var progressMax = 6;
			var progressValue = 0;
			
			var srvc = {
				setBaseState : setBaseState,
				getBaseState : getBaseState,
				setOnFinishState : setOnFinishState,
				getOnFinishState : getOnFinishState,
				resetProgress : resetProgress,
				getProgressMax : getProgressMax,
				getProgressValue : getProgressValue,
				goToPrevState : goToPrevState,
				goToNextState : goToNextState,
				goToNextState2 : goToNextState2,
				goToState : goToState
			};

			init();

			return srvc;

			function init() {
			}
			
			function setBaseState(s) {
				baseState = s;
			}
			
			function getBaseState() {
				return baseState;
			}
			
			function setOnFinishState(s) {
				onFinishState = s;
			}
			
			function getOnFinishState() {
				return onFinishState;
			}
			
			function resetProgress() {
				progressValue = 0;
			}
			
			function getProgressMax() {
				return progressMax;
			}
			
			function getProgressValue() {
				return progressValue;
			}
			
//			function updateProgress(stateName) {
//				switch (stateName) {
//				case baseState + '.create.node0':
//					progressValue = 0;
//					break;
//				case baseState + '.create.node1':
//					progressValue = 1;
//					break;
//				case baseState + '.create.node2':
//					progressValue = 2;
//					break;
//				case baseState + '.create.node3':
//					progressValue = 3;
//					break;
//				case baseState + '.create.node4':
//					progressValue = 4;
//					break;
//				case baseState + '.create.node5':
//					progressValue = 5;
//					break;
//				case baseState + '.create.node6':
//					progressValue = 6;
//					break;
//				case baseState + '.create.node7':
//					progressValue = 7;
//					break;
//				default:
//					break;
//				}
//			}
			function updateProgress(stateName) {
				switch (stateName) {
				case baseState + '.create.node0':
					progressValue = 0;
					break;
				case baseState + '.create.node1':
					progressValue = 1;
					break;
				case baseState + '.create.node2':
					progressValue = 2;
					break;
				case baseState + '.create.node3':
					progressValue = 3;
					break;
				case baseState + '.create.node5':
					progressValue = 4;
					break;
				case baseState + '.create.node6':
					progressValue = 5;
					break;
				case baseState + '.create.node7':
					progressValue = 6;
					break;
				default:
					break;
				}
			}
			
			function goToPrevState(stateName) {
				updateProgress(stateName);
				goToState(stateName, {});
			}
			
			function goToNextState(stateName) {
				updateProgress(stateName);
				goToState(stateName, {});
			}
			
			function goToNextState2(stateName, params) {
				updateProgress(stateName);
				goToState(stateName, params);
			}
			
			function goToState(stateName, params) {
				$state.go(stateName, params, {location: false});
			}
		}
	}
});