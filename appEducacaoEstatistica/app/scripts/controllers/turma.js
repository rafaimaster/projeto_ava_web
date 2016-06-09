'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:TurmaCtrl
 * @description
 * # TurmaCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('TurmaCtrl', function ($scope, $location, $routeParams, $mdDialog, Auth, turmaService, alunoService, anoTurmaService) {
    
  	$scope.turma = {};
  	$scope.turmas = [];
  	$scope.aluno = {};
  	$scope.alunos = [];
  	$scope.anoTurma = {};
  	$scope.anosTurma = [];
  	$scope.alerts = {};
    $scope.promessaLista = "";
  	$scope.promessaFormulario = "";

	$scope.editTurma = function(turma, event) {
    	
    	$location.path("/editar_turma/"+turma.id); 
    }

	$scope.novoAluno = function(turma, event) {
    	$location.path("/cadastro_aluno/"+turma.id); 
    }

    $scope.editAluno = function(aluno, event) {
    	$location.path("/cadastro_aluno/turma/"+aluno.turma.id+"/aluno/"+aluno.id); 
    }

    $scope.verTurma = function(id) {
    	$scope.aluno = {};
		$scope.alunos = [];
    	//console.log(id);
    	$scope.promessaLista = turmaService.buscarPorId(id)
    											.success(function(data, status, headers, config) {
		      					                	$scope.turma = {};
		      					                	$scope.turma = data;
		                      					}).error(function(data, status, headers, config) {
		      					                	if (data.error == undefined || data.error == null) {
														$scope.addAlert("Ocorreu um erro e não foi possível recuperar as turmas cadastradas.", "alert-warning");
													} else {
														$scope.addAlert(data.error, "alert-warning");
													}
		                      					});
		buscarAlunoPorTurma(id);
    }


    //trata a navegacao para edicao
  	if ($routeParams.id != undefined) {
  		$scope.idTurma = $routeParams.id;
  		$scope.verTurma($scope.idTurma);
	}

	$scope.viewListTurma = function() {
		$scope.turma = {};
		$scope.turmas = [];
		$scope.turmas = $scope.buscar();

		//redireciona para a pagina de lista
		$location.path("/lista_turma"); 
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

    $scope.limparFormulario = function() {
    	$scope.clearAlerts();
    	$scope.turma = {};
    }

  	/* =============== FUNÇÕES DE PERSISTENCIA =============== */
    $scope.buscar =  function() {
    	$scope.promessaLista = turmaService.buscar(Auth.getUser().id)
												.success(function(data, status, headers, config) {
													$scope.turmas = [];
													$scope.turmas = data;
												}).error(function(data, status, headers, config) {
													if (data.error == undefined || data.error == null) {
														$scope.addAlert("Ocorreu um erro e não foi possível recuperar as turmas cadastradas.", "alert-warning");
													} else {
														$scope.addAlert(data.error, "alert-warning");
													}
												});
		buscarAnosTurmaAtivos();
    }


  	$scope.addTurma = function() {
		
  		if ($scope.turma.id == null || $scope.turma.id === undefined) {
        	$scope.turma.professor = Auth.getUser();
  	    	$scope.promessaFormulario = turmaService.inserir($scope.turma)
														.success(function(data, status, headers, config) {
															$scope.viewListTurma();
															$scope.limparFormulario();
															$scope.addAlert("Turma criada com sucesso.", "alert-success");
														}).error(function(data, status, headers, config) {
															if (data.error == undefined || data.error == null) {
																$scope.addAlert("Ocorreu um erro e não foi possível atualizar o registro.", "alert-warning");
															} else {
																$scope.addAlert(data.error, "alert-warning");
															}
														});
  		} else {
  			console.log($scope.turma);
        	$scope.promessaFormulario = turmaService.atualizar($scope.turma)
														.success(function (data, status, headers, config) {
															$scope.viewListTurma();
															$scope.clearAlerts();
															$scope.addAlert("Registro atualizado com sucesso.", "alert-success");
														}).error(function (data, status, headers, config) {
															$scope.clearAlerts();
																if (data.error == undefined || data.error == null) {
															$scope.addAlert("Ocorreu um erro e não foi possível atualizar o registro.", "alert-warning");
															} else {
																$scope.addAlert(data.error, "alert-warning");
															}
    													});
      	}
    }

	$scope.confirmaExclusao = function (turma, event) {
		confirm = $mdDialog.confirm()
							.title('Atenção')
							.textContent('Deseja realmente excluir a turma?')
							.ok('Sim')
							.cancel('Não');
		$mdDialog.show( confirm )
					.then(function(){
						removerTurma(turma, event);
					})
					.finally(function() {
						confirm = undefined;
					});
	}
	

    function removerTurma(turma, event) {
		
		var index = $scope.turmas.indexOf(turma);

		$scope.promessaLista = turmaService.deletar(index, turma)
												.success(function(data, status, headers, config) {
													$scope.turmas = $scope.buscar();
													$scope.clearAlerts();
													$scope.addAlert("Registro removido com sucesso.", "alert-success");
												})
												.error(function(data, status, headers, config) {
													$scope.clearAlerts();
													$scope.addAlert("Ocorreu um erro e não foi possível remover o registro.", "alert-warning");
													$scope.addAlert(data.error, "alert-warning");
												});
    }

	$scope.confirmaExclusaoAluno = function (aluno, event) {
	    confirm = $mdDialog.confirm()
	              .title('Atenção')
	              .textContent('Deseja realmente excluir o aluno?')
	              .ok('Sim')
	              .cancel('Não');
	    $mdDialog.show( confirm )
	          .then(function(){
	            removerAluno(aluno, event);
	          })
	          .finally(function() {
	            confirm = undefined;
	          });
	}

    function removerAluno(aluno, event) {
    
	    var index = $scope.alunos.indexOf(aluno);
	    var turma = aluno.turma;

	    //console.log(turma);

	    $scope.promessaLista = alunoService.deletar(aluno, '/aluno/')
	                        .success(function(data, status, headers, config) {
	                          $scope.alunos = buscarAlunoPorTurma(turma.id);
	                          $scope.clearAlerts();
	                          $scope.addAlert("Registro removido com sucesso.", "alert-success");
	                        })
	                        .error(function(data, status, headers, config) {
	                          $scope.clearAlerts();
	                          $scope.addAlert("Ocorreu um erro e não foi possível remover o registro.", "alert-warning");
	                          $scope.addAlert(data.error, "alert-warning");
	                        });
	}

	function buscarAlunoPorTurma(idTurma) {
	    $scope.promessaLista = alunoService.buscarAlunosPorTurma(idTurma)
	                                            .success(function(data, status, headers, config) {
	                                              $scope.aluno = {};
	                                              $scope.alunos = data;
	                                            }).error(function(data, status, headers, config) {
	                                              if (data.error == undefined || data.error == null) {
	                                                $scope.addAlert("Ocorreu um erro e não foi possível recuperar os alunos da turma.", "alert-warning");
	                                              } else {
	                                                $scope.addAlert(data.error, "alert-warning");
	                                              }
                                            });
  	}


  	function  buscarAnosTurmaAtivos() {
  		$scope.promessaLista = anoTurmaService.buscarAnosTurmaAtivos()
	                                            .success(function(data, status, headers, config) {
	                                              $scope.anoTurma = {};
	                                              $scope.anosTurma = data;
	                                            }).error(function(data, status, headers, config) {
	                                              if (data.error == undefined || data.error == null) {
	                                                $scope.addAlert("Ocorreu um erro e não foi possível recuperar os anos para a turma.", "alert-warning");
	                                              } else {
	                                                $scope.addAlert(data.error, "alert-warning");
	                                              }
                                            });	
  	}

    /* inicializa a tela carregando todas as turmas cadastrados para o usuário logado */
    $scope.buscar();

});