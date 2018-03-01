'use strict'

var myHomePageController = myApp.controller('myHomePage', ['$scope', '$cookies','$cookieStore', function ($scope,$cookies,$cookieStore) {
	
	
	console.log("Iam called");
	console.log($cookies.get("heroku-nav-data"));
	
	$scope.data=$cookies.get("heroku-nav-data");
	
}])
;