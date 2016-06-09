'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:MenuCtrl
 * @description
 * # MenuCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('MenuCtrl', function ($scope, $timeout, $mdSidenav, $log, $location) {

	$scope.itens = [
		{path: '/', title: 'INÍCIO'},
		//{path: '/about', title: 'SOBRE'},
		{path: '/conceito', title: 'CONCEITOS'},
		{path: '/pesquisa', title: 'PESQUISA'},
		{path: '/blog', title: 'BLOG'},
		{path: '/formacao_continuada', title: 'LEITURA'},
    {path: '/plano_aula', title: 'PLANO DE AULA'},
    {path: '/forum', title: 'FÓRUM'},
		{path: '/cadastro_usuario', title: 'CADASTRO'}
	];

	$scope.isActive = function(item) {
    console.log("item " + item.path);
    console.log("loc " + $location.path());
    if (item.path == $location.path()) {
      return true;
    }
    return false;
  };

  $scope.closeLeft = function () {
    $mdSidenav('left').close()
      .then(function () {
        $log.debug("close LEFT is done");
      });
  };

  $scope.closeRight = function () {
    $mdSidenav('right').close()
      .then(function () {
        $log.debug("close RIGHT is done");
      });
  };
    
});
