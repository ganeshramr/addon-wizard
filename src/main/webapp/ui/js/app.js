var myApp = angular.module('myApp', ['ngRoute','ngCookies']);

myApp.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/', {
    templateUrl: 'partials/home.html',
    controller: 'myHomePage',
  });
  
  $routeProvider.when('/addClient', {
	    templateUrl: 'partials/clientmngmt/addClient.html'
	  });


  // by default, redirect to site root
    $routeProvider.otherwise({
        redirectTo:'/'
    });
}])