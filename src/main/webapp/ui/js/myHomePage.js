'use strict'

var myHomePageController = myApp.controller('myHomePage', ['$scope', '$cookies','$cookieStore','$http','notify','$window', function ($scope,$cookies,$cookieStore,$http,notify,$window) {
	
	$scope.data=$cookies.get("heroku-nav-data");
	$scope.appId=$cookies.get("app-id");
	var navDataJson = JSON.parse(atob($scope.data));
	console.log(navDataJson)
	$scope.appName=navDataJson.appname;
	
	navDataJson.appname
	
	$scope.provision = function() {
        console.log("GO Provision now!!!")
        
        
        var state = $scope.appId+"::"+$scope.appName;
        var b64state = btoa(state);
        $window.open('https://id.heroku.com/oauth/authorize?client_id=4851600a-c879-4c9e-80b3-22502e5af36f&response_type=code&scope=global&state='+b64state,'_self')
        //return $http({method: 'GET', url: "https://id.heroku.com/oauth/authorize?client_id=4851600a-c879-4c9e-80b3-22502e5af36f&response_type=code&scope=global&state="+b64state});
    };
	
	
}])
;