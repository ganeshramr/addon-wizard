var myApp = angular.module('myApp', ['ngRoute']);

myApp.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/', {
    templateUrl: 'partials/home.html'
  });
  
  $routeProvider.when('/addClient', {
	    templateUrl: 'partials/clientmngmt/addClient.html'
	  });


  // by default, redirect to site root
    $routeProvider.otherwise({
        redirectTo:'/'
    });
}])