'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:AvaliacaopesquisaCtrl
 * @description
 * # AvaliacaopesquisaCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('AvaliacaopesquisaCtrl', function ($scope, $location, $routeParams, $mdDialog, avaliacaoPesquisaService, pesquisaService, perguntaService, CONSTANTES, Auth) {
    //avaliacaoPesquisaService

    $scope.avaliacaoPesquisa = {};
    $scope.perguntas1 = [];
    $scope.perguntas2 = [];
    $scope.tiposGrafico = [];
    $scope.pesquisa = {};
    $scope.alerts = {};
    $scope.promessaLista = "";
    $scope.promessaFormulario = "";

    var isRecuperacaoEdicao = false;

    $scope.labels = [];
	$scope.series = [];
	$scope.data = [];
	$scope.type = '';

	$scope.tiposGrafico = [];
    $scope.tipoGrafico = undefined;

    $scope.buscarPesquisasChart = function(idPergunta1, idPergunta2) {
    	var idP1 = (idPergunta1 == undefined) ? 0 : idPergunta1;
    	var idP2 = (idPergunta2 == undefined) ? 0 : idPergunta2;
		$scope.promessaLista = pesquisaService.buscarPesquisasChart(idP1, idP2)
							.success(function(data, status, headers, config){
								$scope.mostraLegenda = false;
								$scope.labels = data.labels;
								$scope.series = data.series;

								var sizeDados = data.dados.length;
								var sizeDados1 = data.dados1.length;
								
								var idTipo = angular.fromJson($scope.tipoGrafico).id;
								$scope.type = idTipo;

								if (sizeDados > 0) {
									$scope.data = data.dados;	
								} else if (sizeDados1 > 0) {
									if(idTipo === 'Bar' || idTipo ==='Line') {
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

	$scope.trocarGrafico = function(){
		$scope.montarGrafico();
	}

	$scope.montarGrafico = function() {
		var tipoChart = angular.fromJson($scope.tipoGrafico);
		//console.log(tipoChart);
		if (tipoChart != undefined && tipoChart != null) {
			var p1 = angular.fromJson($scope.avaliacaoPesquisa.pergunta1);
			var p2 = angular.fromJson($scope.avaliacaoPesquisa.pergunta2);

			$scope.data = [];
			$scope.labels = [];
			$scope.series = [];
			
			if ((p1 != undefined || p1 != null) && (p2 != undefined || p2 != null)) {
				$scope.buscarPesquisasChart(p1.id, p2.id);
			} else if ((p1 != undefined || p1 != null) && (p2 == undefined || p2 == null)) {
				$scope.buscarPesquisasChart(p1.id, 0);
			} else {
				console.log('Pergunta 1 não selecionada!');
			}
		}
	}

	$scope.montarPerguntas2 = function(){

		$scope.tiposGrafico = [
			{ id: '', nome: '-- Selecione --' },
			{ id: 'Line', nome: 'Linhas' },
			{ id: 'Bar', nome: 'Barras' },
			{ id: 'Doughnut', nome: 'Rosca'},
			{ id: 'Pie', nome: 'Pizza'}
	    ];


		var p = angular.fromJson($scope.avaliacaoPesquisa.pergunta1);
		
		if(!isRecuperacaoEdicao) {
			$scope.avaliacaoPesquisa.pergunta2  = undefined;
		}
		$scope.perguntas2 = [];
		isRecuperacaoEdicao = false;
		
		for(var index in $scope.perguntas1) {
			var pergunta = $scope.perguntas1[index];
			if (pergunta.id != p.id) {
				$scope.perguntas2.push(pergunta);
			}
		}
		$scope.trocarGrafico();
	}

	$scope.changePergunta2 = function() {
		$scope.tiposGrafico = [
			{ id: '', nome: '-- Selecione --' },
			{ id: 'Line', nome: 'Linhas' },
			{ id: 'Bar', nome: 'Barras' }
	    ];
	    $scope.tipoGrafico = undefined;
		$scope.trocarGrafico();	
	}

	//trata a navegacao para cadastro da avaliação da pesquisa
	if ($routeParams.idPesquisaAvaliacao != undefined) {
		var idPesquisaAvaliacao = $routeParams.idPesquisaAvaliacao;

		buscarPorIdPesquisa(idPesquisaAvaliacao);

		buscarPesquisaPorId(idPesquisaAvaliacao);

		buscarPerguntasPorPesquisa(idPesquisaAvaliacao);

	}

	function buscarPorIdPesquisa(idPesquisaAvaliacao) {
		$scope.promessaLista = avaliacaoPesquisaService.buscarPorIdPesquisa(idPesquisaAvaliacao)
		                                              		.success(function(data, status, headers, config) {
		          		      					                $scope.avaliacaoPesquisa = {};
		          		      					                $scope.avaliacaoPesquisa = data;
																isRecuperacaoEdicao = true;
		          		      					                $scope.montarPerguntas2();

		          		      					                $scope.tipoGrafico = { id: data.tipoGrafico };

		          		      					                $scope.trocarGrafico();

		          		                      				}).error(function(data, status, headers, config) {
		          		      					                if (data.error == undefined || data.error == null) {
		                      										$scope.addAlert("Ocorreu um erro e não foi possível recuperar a avaliação da pesquisa.", "alert-warning");
		                      									} else {
		                      										$scope.addAlert(data.error, "alert-warning");
		                      									}
		                                              		});
	}

	function buscarPesquisaPorId(id) {
    	$scope.pesquisa = {};
    	
    	$scope.promessaLista = pesquisaService.buscarPorId(id)
                                              		.success(function(data, status, headers, config) {
          		      					                $scope.pesquisa = {};
          		      					                $scope.pesquisa = data;
          		                      				}).error(function(data, status, headers, config) {
          		      					                if (data.error == undefined || data.error == null) {
                      										$scope.addAlert("Ocorreu um erro e não foi possível recuperar a avaliação da pesquisa.", "alert-warning");
                      									} else {
                      										$scope.addAlert(data.error, "alert-warning");
                      									}
                                              		});
    }

    function buscarPerguntasPorPesquisa(idPesquisaAvaliacao) {
    	$scope.promessaLista = perguntaService.buscarPerguntaPorPesquisa(idPesquisaAvaliacao)
													.success(function(data, status, headers, config){
														$scope.perguntas1 = data;
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


	$scope.addAvaliacaoPesquisa = function() {

		//$scope.avaliacaoPesquisa.pergunta1 = angular.fromJson($scope.pergunta1);
		//$scope.avaliacaoPesquisa.pergunta2 = angular.fromJson($scope.pergunta2);
		$scope.avaliacaoPesquisa.tipoGrafico = angular.fromJson($scope.tipoGrafico).id;
		$scope.avaliacaoPesquisa.pesquisa = $scope.pesquisa;

  		if ($scope.avaliacaoPesquisa.id == null || $scope.avaliacaoPesquisa.id === undefined) {

  			$scope.promessaFormulario = avaliacaoPesquisaService.inserir($scope.avaliacaoPesquisa)
  															.success(function(data, status, headers, config) {
                                                              //$scope.viewListPesquisa();
                                                              $scope.avaliacaoPesquisa = {};
                                                              $scope.clearAlerts(); 
                                                              $scope.addAlert("Registro inserido com sucesso.", "alert-success");
                                                            }).error(function(data, status, headers, config) {
                                                              $scope.clearAlerts();
                                                              $scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
                                                              $scope.addAlert(data.error, "alert-warning");
                                                            });

  		} else {

  			$scope.promessaFormulario = avaliacaoPesquisaService.atualizar($scope.avaliacaoPesquisa)
  															.then(function success(response) {
	  												          //$scope.viewListPesquisa();
	  												          $scope.clearAlerts();
	  												          $scope.addAlert("Registro atualizado com sucesso.", "alert-success");
	  												        }, function error(response) {
	  												          //console.log(response);
	  												          $scope.clearAlerts();
	  												          $scope.addAlert("Ocorreu um erro e não foi possível atualizar o registro.", "alert-warning");
	  												          $scope.addAlert(response.error, "alert-warning");
	  												        });
  		}
       		
  	}

});
