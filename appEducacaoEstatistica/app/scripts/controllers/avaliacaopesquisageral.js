'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:AvaliacaopesquisageralCtrl
 * @description
 * # AvaliacaopesquisageralCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('AvaliacaopesquisageralCtrl', function ($scope, $location, $routeParams, avaliacaoPesquisaService, pesquisaService, CONSTANTES, Auth) {
    
  	$scope.avaliacaoPesquisa = {};
  	$scope.possuiAvaliacao = false;
  	$scope.labels = [];
	$scope.series = [];
	$scope.data = [];
	$scope.type = '';

	//trata a navegacao para cadastro da avaliação da pesquisa
	if ($routeParams.idPesquisaAvaliacao != undefined) {
		var idPesquisaAvaliacao = $routeParams.idPesquisaAvaliacao;

		buscarPorIdPesquisa(idPesquisaAvaliacao);

	}

  	function buscarPorIdPesquisa(idPesquisaAvaliacao) {
		$scope.promessaLista = avaliacaoPesquisaService.buscarPorIdPesquisa(idPesquisaAvaliacao)
		                                              		.success(function(data, status, headers, config) {
		          		      					                $scope.avaliacaoPesquisa = {};
		          		      					                $scope.avaliacaoPesquisa = data;


		          		      					                if ( ($scope.avaliacaoPesquisa != undefined) && ($scope.avaliacaoPesquisa.id == null || $scope.avaliacaoPesquisa.id == undefined)) {
		          		      					                	$scope.possuiAvaliacao = false;
		          		      					                } else {
		          		      					                	$scope.possuiAvaliacao = true;
			          		      					                //console.log(data);

			          		      					                if (($scope.avaliacaoPesquisa.pergunta1 != undefined && $scope.avaliacaoPesquisa.pergunta1 != null) 
			          		      					                	&& ($scope.avaliacaoPesquisa.pergunta2 != undefined && $scope.avaliacaoPesquisa.pergunta2 != null)) {
			          		      					                	$scope.buscarPesquisasChart($scope.avaliacaoPesquisa.pergunta1.id, $scope.avaliacaoPesquisa.pergunta2.id, data.tipoGrafico);
			          		      					        		} else if (($scope.avaliacaoPesquisa.pergunta1 != undefined && $scope.avaliacaoPesquisa.pergunta1 != null)
			          		      					        					&& ($scope.avaliacaoPesquisa.pergunta2 == undefined && $scope.avaliacaoPesquisa.pergunta2 == null)) {
																		$scope.buscarPesquisasChart($scope.avaliacaoPesquisa.pergunta1.id, undefined, data.tipoGrafico);
			          		      					        		}
			          		      					        	}

		          		                      				}).error(function(data, status, headers, config) {
		          		      					                if (data.error == undefined || data.error == null) {
		                      										$scope.addAlert("Ocorreu um erro e não foi possível recuperar a avaliação da pesquisa.", "alert-warning");
		                      									} else {
		                      										$scope.addAlert(data.error, "alert-warning");
		                      									}
		                                              		});
		
	}

	$scope.buscarPesquisasChart = function(idPergunta1, idPergunta2, tipoGrafico) {
    	var idP1 = (idPergunta1 == undefined) ? 0 : idPergunta1;
    	var idP2 = (idPergunta2 == undefined) ? 0 : idPergunta2;
		$scope.promessaLista = pesquisaService.buscarPesquisasChart(idP1, idP2)
							.success(function(data, status, headers, config){	

								$scope.labels = data.labels;
								$scope.series = data.series;

								var sizeDados = data.dados.length;
								var sizeDados1 = data.dados1.length;
								
								$scope.type = tipoGrafico;

								if (sizeDados > 0) {
									$scope.data = data.dados;	
								} else if (sizeDados1 > 0) {
									if(tipoGrafico === 'Bar' || tipoGrafico ==='Line') {
										$scope.series = [];
										$scope.data = [data.dados1];
									} else {
										$scope.data = data.dados1;
									}
								}
							}).error(function(){
								$scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
		                        $scope.addAlert(data.error, "alert-warning");
							});
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

  });
