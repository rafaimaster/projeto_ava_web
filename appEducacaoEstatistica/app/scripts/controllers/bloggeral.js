'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:BloggeralCtrl
 * @description
 * # BloggeralCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('BloggeralCtrl', function ($scope, blogService) {
    
  $scope.postsPublicados = [];
  $scope.alerts = {};
  $scope.promessaLista = "";
  $scope.promessaFormulario = "";


/* AREA PUBLICACAO */
  function buscarPostsPublicados(){
     $scope.promessaLista = blogService.buscarPostsPublicados()
                                          .success(function(data, status, headers, config) {
                                            $scope.postsPublicados = [];
                                            $scope.postsPublicados = data;
                                          }).error(function(data, status, headers, config) {
                                            $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
                                            $scope.addAlert(data.error, "alert-warning");
                                          });
  }


  buscarPostsPublicados();

  });
