'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:PesquisageralCtrl
 * @description
 * # PesquisageralCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('PesquisageralCtrl', function ($scope, pesquisaService) {
    
	$scope.pesquisasPublicadas = [];
	$scope.pesquisasEncerradas = [];
	
	/*
	$scope.labels = [];
	$scope.series = [];
	$scope.data = [];
	*/

	/* AREA PUBLICACAO */

	/*
	$scope.buscarPesquisasChart = function() {
		
		$scope.promessaLista = pesquisaService.buscarPesquisasChart()
							.success(function(data, status, headers, config){
								console.log(data);
								$scope.labels = data.labels;
								$scope.series = data.series;
								$scope.data = data.dados;
							}).error(function(){
								$scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
		                        $scope.addAlert(data.error, "alert-warning");
							});
		
		$scope.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
  		//$scope.series = ['Series A'];

  		$scope.data = [[65, 59, 80, 81, 56, 55, 40]];
	}
	*/

	function buscarPesquisasPublicadas(){
	 $scope.promessaLista = pesquisaService.buscarPesquisasPublicadas()
	                                      .success(function(data, status, headers, config) {
	                                        $scope.pesquisasPublicadas = [];
	                                        $scope.pesquisasPublicadas = data;
	                                      }).error(function(data, status, headers, config) {
	                                        $scope.addAlert("Ocorreu um erro e não foi possível recuperar as pesquisas publicadas.", "alert-warning");
	                                        $scope.addAlert(data.error, "alert-warning");
	                                      });
		//$scope.buscarPesquisasChart();	                                      
	}

	function buscarPesquisasEncerradas(){
	 $scope.promessaLista = pesquisaService.buscarPesquisasEncerradas()
	                                      .success(function(data, status, headers, config) {
	                                        $scope.pesquisasEncerradas = [];
	                                        $scope.pesquisasEncerradas = data;
	                                      }).error(function(data, status, headers, config) {
	                                        $scope.addAlert("Ocorreu um erro e não foi possível recuperar as pesquisas encerradas.", "alert-warning");
	                                        $scope.addAlert(data.error, "alert-warning");
	                                      });
	}

	function buscarPesquisas() {
		buscarPesquisasPublicadas();
		buscarPesquisasEncerradas();
	}

	buscarPesquisas();


  });
