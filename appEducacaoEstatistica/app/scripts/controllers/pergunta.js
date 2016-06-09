'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:PerguntaCtrl
 * @description
 * # PerguntaCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('PerguntaCtrl', function ($scope, $location, $routeParams, $mdDialog, perguntaService, pesquisaService, alternativaService, CONSTANTES, Auth) {
    

	$scope.pergunta = {};
    $scope.perguntas = [];
    $scope.pesquisa = {};
    $scope.alternativa = {};
    $scope.alternativas = [];
    $scope.alerts = {};
    $scope.promessaLista = "";
    $scope.promessaFormulario = "";





 	$scope.addAlert = function(message, type) {
      $scope.alerts[type] = this.alerts[type] || [];
      $scope.alerts[type].push(message);
    }

    $scope.clearAlerts = function() {
      for(var msg in $scope.alerts) {
        delete this.alerts[msg];
      }
    }


    //trata a navegacao para edicao
  	if ($routeParams.idPesquisa != undefined) {

	    var idPesquisa = $routeParams.idPesquisa;
	    var idPergunta = $routeParams.idPergunta;

		if (idPesquisa != undefined) {	    
			buscarPesquisa(idPesquisa);
		}
	    if (idPesquisa === undefined) {
	    	buscarPesquisa(idPesquisa);
	    } else if (idPesquisa != undefined && idPergunta != undefined) {
	      buscarPergunta(idPesquisa, idPergunta);
	    }
  		
	}

	
	
	$scope.limparFormulario = function() {
		$scope.clearAlerts();

		if($scope.pergunta.pesquisa != undefined) {
			$location.path("/editar_pesquisa/"+$scope.pergunta.pesquisa.id); 
		} else if ($scope.pesquisa != undefined) {
			$location.path("/editar_pesquisa/"+$scope.pesquisa.id); 
		}
		$scope.pergunta = {};
	}


	$scope.addPergunta = function() {

  		if ($scope.pergunta.id == null || $scope.pergunta.id === undefined) {
  			$scope.pergunta.pesquisa = $scope.pesquisa;
        $scope.pergunta.pesquisa.autor = null;
  			$scope.promessaFormulario = perguntaService.inserir($scope.pergunta)
                                                      .success(function(data, status, headers, config) {
                                                        //$scope.limparFormulario();
                                                        $scope.pergunta = data;
                                                        $scope.clearAlerts(); 
                                                        $scope.addAlert("Registro inserido com sucesso.", "alert-success");
                                                      }).error(function(data, status, headers, config) {
                                                        $scope.clearAlerts();
                                                        $scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
                                                        $scope.addAlert(data.error, "alert-warning");
                                                      });
  		} else {
  			
  			$scope.promessaFormulario = perguntaService.atualizar($scope.pergunta).then(
  												        function success(response) {
  												        	//$scope.limparFormulario();
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

	function buscarPesquisa (idPesquisa) {

    	$scope.promessaLista = pesquisaService.buscarPorId(idPesquisa)
    											.success(function(data, status, headers, config) {
		      					                	$scope.pesquisa = {};
		      					                	$scope.pesquisa = data;
		                      					}).error(function(data, status, headers, config) {
		      					                	if (data.error == undefined || data.error == null) {
														$scope.addAlert("Ocorreu um erro e não foi possível recuperar as turmas cadastradas.", "alert-warning");
													} else {
														$scope.addAlert(data.error, "alert-warning");
													}
		                      					});
    }

  


	function buscarPergunta(idPesquisa, idPergunta) {
		$scope.promessaLista = perguntaService.buscarPergunta(idPesquisa, idPergunta)
                                              .success(function(data, status, headers, config) {
                                                $scope.pergunta = {};
                                                $scope.pergunta = data;
                                              }).error(function(data, status, headers, config) {
                                                if (data.error == undefined || data.error == null) {
                                                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar o aluno.", "alert-warning");
                                                } else {
                                                  $scope.addAlert(data.error, "alert-warning");
                                                }
                                              });

    buscarAlternativasPorPergunta(idPergunta);
	}

	$scope.addAlternativa = function() {

    var alt = {};

    alt.id = '';
    alt.descricao = $scope.alternativa.descricao;
    alt.pergunta = $scope.pergunta;

    $scope.promessaFormulario = alternativaService.inserir(alt)
                                                    .success( function(data, status, headers, config) {
                                                      //$scope.clearAlerts(); 
                                                      //$scope.addAlert("Registro inserido com sucesso.", "alert-success");

                                                      $scope.alternativas = [];
                          
                                                      $scope.alternativas = buscarAlternativasPorPergunta($scope.pergunta.id);

                                                      //$scope.alternativas.push($scope.alternativa);
                                                      $scope.alternativa = {};

                                                    }).error(function(data, status, headers, config) {
                                                      $scope.clearAlerts();
                                                      $scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
                                                      $scope.addAlert(data.error, "alert-warning");
                                                    });

    }


    function buscarAlternativasPorPergunta(idPergunta) {

      $scope.promessaFormulario = alternativaService.buscarAlternativaPorPergunta(idPergunta)
                                              .success(function(data, status, headers, config) {
                                                $scope.alternativas = [];
                                                $scope.alternativas = data;
                                              }).error(function(data, status, headers, config) {
                                                if (data.error == undefined || data.error == null) {
                                                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar o aluno.", "alert-warning");
                                                } else {
                                                  $scope.addAlert(data.error, "alert-warning");
                                                }
                                              });

    }


$scope.confirmaExclusaoAlternativa = function (alternativa, event) {
    confirm = $mdDialog.confirm()
              .title('Atenção')
              .textContent('Deseja realmente excluir essa opção?')
              .ok('Sim')
              .cancel('Não');
    $mdDialog.show( confirm )
          .then(function(){
            removerAlternativa(alternativa, event);
          })
          .finally(function() {
            confirm = undefined;
          });
  }
  

  function removerAlternativa(alternativa, event) {

    $scope.promessaLista = alternativaService.deletar(alternativa)
                        .success(function(data, status, headers, config) {
                          $scope.alternativas = [];

                          $scope.alternativas = buscarAlternativasPorPergunta($scope.pergunta.id);
                          $scope.clearAlerts();
                          $scope.addAlert("Registro removido com sucesso.", "alert-success");
                        })
                        .error(function(data, status, headers, config) {
                          $scope.clearAlerts();
                          $scope.addAlert("Ocorreu um erro e não foi possível remover o registro.", "alert-warning");
                          $scope.addAlert(data.error, "alert-warning");
                        });
    }

  });
