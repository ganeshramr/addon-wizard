'use strict'

var myHomePageController = myApp.controller('myHomePage', ['$scope', '$cookies','$cookieStore','$http', function ($scope,$cookies,$cookieStore,$http) {
	
	
	console.log("Iam called");
	console.log($cookies.get("heroku-nav-data"));
	
	$scope.data=$cookies.get("heroku-nav-data");
	//$scope.data="eyJhcHBuYW1lIjoiYWRkb24tdGVzdGVyLTIiLCJhZGRvbiI6ImJsb2NrY2hhaW4gVGVzdCIsImFkZG9ucyI6W3siY3VycmVudCI6dHJ1ZSwiaWNvbiI6Imh0dHBzOi8vYWRkb25zLmhlcm9rdS5jb20vcHJvdmlkZXIvYWRkb25zL2Jsb2NrY2hhaW4vaWNvbnMvbWVudS9wcm9jZXNzZWQucG5nIiwic2x1ZyI6ImJsb2NrY2hhaW46dGVzdCIsIm5hbWUiOiJibG9ja2NoYWluIn1dfQ";
	var navDataJson = JSON.parse(atob($scope.data));
	console.log(navDataJson)
	$scope.appName=navDataJson.appname;
	
	navDataJson.appname
	
	$scope.provision = function() {
        console.log("GO Provision now!!!")
        
        
        var d = '{' + '\"appName\"' + ':' + '\"'+$scope.appName+'\"' + '}';
        return $http({method: 'POST', url: "https://blockchain-deploy-wizard.herokuapp.com/rest/heroku/deploy", data: d,headers: {
            'Authorization': 'Basic YmxvY2tjaGFpbjplYjIwZDkwN2YzY2YxNmRlNzQyOGEyNjUwYWJkNDA4NA=='}})
        .then(function (success) {
            console.log(success);
            
        },function (error){
        	console.log(data);
        });
    };
	
	
}])
;