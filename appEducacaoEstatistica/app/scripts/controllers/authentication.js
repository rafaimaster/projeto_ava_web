'use strict';

/**
 * @ngdoc function
 * @name educacaoestatisticaApp.controller:AuthenticationCtrl
 * @description
 * # AuthenticationCtrl
 * Controller of the educacaoestatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('AuthenticationCtrl', function ($scope, $http, $sessionStorage, Auth, loginService, $location) {

	$scope.usuarioLogado = {};
  $scope.loginUsuario = {};
  $scope.promessaFormulario = {};
  $scope.alerts = {};

  $scope.itensUsuario = [];

	$scope.pageLogin = function() {
		$location.path("/login");
	}

$scope.addAlert = function(message, type) {
      $scope.alerts[type] = this.alerts[type] || [];
      $scope.alerts[type].push(message);
    }

    $scope.clearAlerts = function() {
      for(var msg in $scope.alerts) {
        delete this.alerts[msg];
      }
    }

  	$scope.login = function() {
  		$scope.promessaFormulario = loginService.login($scope.loginUsuario).success(
                function(data, status, headers, config) {
                  
                  $scope.usuarioLogado = data;
                	Auth.setUser(data, true);
                	
                	$scope.limparFormulario();

                	$location.path("/");

                }).error(function(data, status, headers, config) {
                	$scope.usuarioLogado = {};
                	delete $sessionStorage.usuarioLogado;
                	delete $sessionStorage.isLogado;

               		$scope.clearAlerts();
                	$scope.addAlert(data.error, "alert-warning");
                });
  	}

  	$scope.logout = function() {
      var loginUsuario = Auth.getUser().loginUsuario;
      //nsole.log(loginUsuario);
  		loginService.logout(loginUsuario).success(
                function(data, status, headers, config) {

                	$scope.usuarioLogado = {};
                	$scope.isLogado = false;
                	delete $sessionStorage.usuarioLogado;
                	delete $sessionStorage.isLogado;

                  $location.path("/");

                }).error(function(data, status, headers, config) {
                  	$scope.usuarioLogado = {};
                  	$scope.isLogado = false;
                  	delete $sessionStorage.usuarioLogado;
                  	delete $sessionStorage.isLogado;

                	$scope.clearAlerts();
                	$scope.addAlert(data.error, "alert-warning");
                });
  	}

  	$scope.limparFormulario = function() {
  		$scope.loginUsuario = {};
  	}

    $scope.estaLogado = function() {
      return Auth.isLogado();
    }

    $scope.nomeUsuarioLogado = function() {
      var user = Auth.getUser();
      if (user != null && user != undefined) {
        return user.nome;
      }
      return "";
    }

});






/* LOGIN COM FACEBOOK 
$scope.user = {};
	$scope.logged = false;
	$scope.byebye = false;
    $scope.salutation = false;

  
	$scope.$watch( function() {
	  return Facebook.isReady();
	}, function(newVal) {
		if (newVal)
		$scope.facebookReady = true;
	});

	
	$scope.getLoginStatus = function() {
      Facebook.getLoginStatus(function(response) {
        if(response.status === 'connected') {
          $scope.logged = true;
        } else {
          $scope.logged = false;
        }
      });
    };

	
	$scope.IntentLogin = function() {
		if(!$scope.logged) {
		  $scope.login();
		}
	};

  
	$scope.login = function() {
		Facebook.login(function(response) {
			if (response.status == 'connected') {
				$scope.logged = true;
				$scope.me();
			}
		}, {scope: 'email'});
	};

       
      $scope.logout = function() {
        Facebook.logout(function() {
          $scope.$apply(function() {
            $scope.user   = {};
            $scope.logged = false;  
          });
        });
      }

    $scope.me = function() {
      Facebook.api('/me', {fields: 'name, last_name,email'}, function(response) {
        $scope.user = response;
        console.log($scope.user);
      });
    };


*/
