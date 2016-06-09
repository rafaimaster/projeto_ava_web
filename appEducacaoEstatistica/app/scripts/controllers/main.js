'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp' )
  .controller('MainCtrl', function ($scope, $timeout, $mdSidenav, $log, Auth) {
    
  $scope.itensUsuario = [];

	$scope.toggleLeft = buildToggler('left');
    $scope.toggleRight = buildToggler('right');
    $scope.isOpenRight = function(){
      return $mdSidenav('right').isOpen();
    };
    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait, context) {
      var timer;
      return function debounced() {
        var context = $scope, args = Array.prototype.slice.call(arguments);
        $timeout.cancel(timer);
        timer = $timeout(function() {
          timer = undefined;
          func.apply(context, args);
        }, wait || 10);
      };
    }
    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
    function buildDelayedToggler(navID) {
      return debounce(function() {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
            $log.debug("toggle " + navID + " is done");
          });
      }, 200);
    }
    function buildToggler(navID) {
      return function() {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
            if (navID === 'left') {
              $scope.getMenuUsuario();
            }
          });
      }
    }

  $scope.getMenuUsuario = function() {
    $scope.itensUsuario = [];

    if (Auth.getUser() != null) {
      
      var perfil = Auth.getUser().perfilUsuario;

      if (perfil != null && perfil != undefined) {

        if (perfil.id === 1) {//perfil professor
          $scope.itensUsuario = [
            {path: '/forum', title: 'FÓRUM'},
            {path: '/lista_turma', title: 'TURMA'},
            {path: '/lista_blog', title: 'BLOG'},
            {path: '/lista_pesquisa', title: 'PESQUISA'}
          ];
        } else if (perfil.id === 2) {//perfil aluno
          $scope.itensUsuario = [
            {path: '/lista_blog', title: 'BLOG'},
            {path: '/lista_pesquisa', title: 'PESQUISA'}
          ];
        }
      }
    }
  }

  $scope.limpaMenuApp = function() {
    $scope.itensUsuario = [];
  }

});
