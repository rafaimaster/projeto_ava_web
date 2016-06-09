'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:ResponderpesquisaCtrl
 * @description
 * # ResponderpesquisaCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('ResponderpesquisaCtrl', function ($scope, $routeParams, pesquisaService) {
    
	$scope.pesquisaVO = {};
	$scope.respostaVO = {};
	$scope.respostasVO = [];

	$scope.alerts = {};
    $scope.promessaLista = "";


	if ($routeParams.idPesquisaResposta != undefined) {
  		var idPesquisaResposta = $routeParams.idPesquisaResposta;
  		responderPesquisa(idPesquisaResposta);
	}


	function responderPesquisa(idPesquisaResposta) {
    	$scope.pesquisaVO = {};
    	$scope.promessaLista = pesquisaService.buscarPesquisaPorIdParaResponder(idPesquisaResposta)
    											.success(function(data, status, headers, config) {
		      					                	$scope.pesquisaVO = {};
		      					                	$scope.pesquisaVO = data;
			                					}).error(function(data, status, headers, config) {
								                	if (data.error == undefined || data.error == null) {
														$scope.addAlert("Ocorreu um erro e não foi possível recuperar as postagens cadastradas.", "alert-warning");
													} else {
														$scope.addAlert(data.error, "alert-warning");
													}
			                					});
    }

    $scope.enviarResposta = function() {
    	console.log($scope.respostasVO);
    	
    	$scope.promessaLista = pesquisaService.responderPesquisa($scope.respostasVO)
    											.success(function(data, status, headers, config) {
    												$scope.respostasVO = [];
    												$scope.pesquisaVO = undefined;
		      					                	$scope.addAlert("Obrigado pela sua participação.", "alert-success");
			                					}).error(function(data, status, headers, config) {
								                	if (data.error == undefined || data.error == null) {
														$scope.addAlert("Ocorreu um erro e não foi possível gravar sua resposta.", "alert-warning");
													} else {
														$scope.addAlert(data.error, "alert-warning");
													}
			                					});
    }

    $scope.addResposta = function(perguntaVO, alternativaVO) {
    	
		var respostaVO = {};
		
		/* se for uma pergunta que já foi respondida a resposta é removida da lista 
			para ser incluída novamente com a nova alternativa selecionada */
		if ($scope.respostasVO.length > 0) {
			for(var indice in $scope.respostasVO) {
				var resposta = $scope.respostasVO[indice];
				if (resposta.perguntaVO.id === perguntaVO.id) {
					delete $scope.respostasVO[indice];
				}
			}
		}
		respostaVO = {};			
		//respostaVO.descricao = 'b';
		respostaVO.perguntaVO = perguntaVO;
		respostaVO.alternativaVO = alternativaVO;
		$scope.respostasVO.push(respostaVO);
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
