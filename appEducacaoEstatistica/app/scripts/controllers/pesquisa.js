'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:PesquisaCtrl
 * @description
 * # PesquisaCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('PesquisaCtrl', function ($scope, $location, $routeParams, $mdDialog, pesquisaService, perguntaService, CONSTANTES, Auth) {
    

    $scope.pesquisa = {};
    $scope.pesquisas = [];
    $scope.pesquisasView = [];
    $scope.pergunta = {};
    $scope.perguntas = [];
    $scope.alerts = {};
    $scope.promessaLista = "";
    $scope.promessaFormulario = "";
    $scope.minDate = new Date();

    /* =============== FUNÇÕES DE CONTROLE =============== */
    $scope.novaPergunta = function(pesquisa, event) {
      $location.path("/cadastro_pergunta/pesquisa/"+pesquisa.id); 
    }


    $scope.editPesquisa = function(pesquisa, event) {
    	if (pesquisaEhDoUsuarioLogado(pesquisa.autor)) {
    		$location.path('/editar_pesquisa/'+pesquisa.id);
    	} else {
    		alert = $mdDialog.confirm()
								.title('Alerta')
								.textContent('Não é permitido editar a pesquisa do aluno!')
								.ok('Ok');
			$mdDialog.show( alert )
						.then(function(){
						})
						.finally(function() {
							alert = undefined;
						});
    	}
    }

	function verPesquisa(id) {
    	$scope.pesquisa = {};
    	
    	$scope.promessaLista = pesquisaService.buscarPorId(id)
                                              .success(function(data, status, headers, config) {
          		      					                	$scope.pesquisa = {};
          		      					                	$scope.pesquisa = data;
                                                if (data.dataEncerramento != null && data.dataEncerramento != undefined) {
                                                  $scope.pesquisa.dataEncerramento = new Date(data.dataEncerramento);  
                                                }
                                                
          		                      					}).error(function(data, status, headers, config) {
          		      					                	if (data.error == undefined || data.error == null) {
                      														$scope.addAlert("Ocorreu um erro e não foi possível recuperar as pesquisas cadastradas.", "alert-warning");
                      													} else {
                      														$scope.addAlert(data.error, "alert-warning");
                      													}
                                              });
      buscarPerguntaPorPesquisa(id);
    }

	//trata a navegacao para edicao
  	if ($routeParams.id != undefined) {
  		var id = $routeParams.id;
  		verPesquisa(id);
	}

  $scope.editPergunta = function(pergunta, event) {
    $location.path("/cadastro_pergunta/pesquisa/"+$scope.pesquisa.id+"/pergunta/"+pergunta.id); 
  }

	$scope.viewListPesquisa = function() {
		$scope.pesquisa = {};
		$scope.pesquisas = [];
    	$scope.pesquisas = $scope.buscarMinhasPesquisas();

		//redireciona para a pagina de lista
		$location.path("/lista_pesquisa"); 
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

    /* =============== FUNÇÕES DE CONSULTA =============== */
    $scope.buscarTodos =  function(){
       $scope.promessaLista = pesquisaService.buscarTodos().success(
								                function(data, status, headers, config) {
								                  $scope.pesquisas = [];
								                  $scope.pesquisas = data;
								                }).error(function(data, status, headers, config) {
								                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
								                  $scope.addAlert(data.error, "alert-warning");
								                });
    }

    $scope.buscarMinhasPesquisas =  function() {
       $scope.promessaLista = pesquisaService.buscarMinhasPesquisas(Auth.getUser().id).success(
                function(data, status, headers, config) {
                  $scope.pesquisas = [];
                  $scope.pesquisas = data;
                }).error(function(data, status, headers, config) {
                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
    }
    
    $scope.buscarPesquisasDosAlunos =  function() {
    	var user = Auth.getUser();

      $scope.promessaLista = pesquisaService.buscarPesquisasDosAlunos(user.id).success(
                function(data, status, headers, config) {
                  $scope.pesquisas = [];
                  $scope.pesquisas = data;
                }).error(function(data, status, headers, config) {
                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados para os alunos.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
    }


    /* =============== FUNÇÕES DE PERSISTENCIA =============== */

  	$scope.addPesquisa = function() {

  		if ($scope.pesquisa.id == null || $scope.pesquisa.id === undefined) {

  			var user = Auth.getUser();

  			if (user.perfilUsuario.id === 2) {
  				delete user.turma;
  			}

  			$scope.pesquisa.autor = user;

  			$scope.promessaFormulario = pesquisaService.inserir($scope.pesquisa).success(
  																			function(data, status, headers, config) {
  		                                                                      $scope.viewListPesquisa();
  		                                                                      $scope.clearAlerts(); 
  		                                                                      $scope.addAlert("Registro inserido com sucesso.", "alert-success");
  		                                                                    }).error(function(data, status, headers, config) {
  		                                                                      $scope.clearAlerts();
  		                                                                      $scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
  		                                                                      $scope.addAlert(data.error, "alert-warning");
  		                                                                    });
  		} else {
  			var user = $scope.pesquisa.autor;

  			if (user.perfilUsuario.id === 2) {
  				delete user.turma;
  			}

  			$scope.pesquisa.autor = user;
  			$scope.promessaFormulario = pesquisaService.atualizar($scope.pesquisa).then(
  												        function success(response) {
  												          $scope.viewListPesquisa();
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

  	$scope.confirmaPublicacao = function (pesquisa, event) {
		confirm = $mdDialog.confirm()
							.title('Atenção')
							.textContent('Deseja realmente publicar a pesquisa?')
							.ok('Sim')
							.cancel('Não');
		$mdDialog.show( confirm )
					.then(function(){
						publicar(pesquisa, event);
					})
					.finally(function() {
						confirm = undefined;
					});
	}

  	function publicar(pesquisa, event) {

      /* trata a situação da publicação do post conforme o perfil do usuário */
      if (Auth.isProfessor()) {
        pesquisa.situacao.id = 3;//3-Publicado
      } else {
        pesquisa.situacao.id = 2;//2-Avaliação
      }

      var user = pesquisa.autor;

		if (user.perfilUsuario.id === 2) {
			delete user.turma;
		}

		pesquisa.autor = user;

      $scope.promessaLista = pesquisaService.atualizar(pesquisa).success(
                function(data, status, headers, config) {
                  $scope.viewListPesquisa();

                  $scope.clearAlerts();
                  $scope.addAlert("Registro publicado com sucesso.", "alert-success");
                })
                .error(function(data, status, headers, config) {
                  $scope.viewListPesquisa();

                  $scope.clearAlerts();
                  $scope.addAlert("Ocorreu um erro e não foi possível atualizar o registro.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
  	}

  	$scope.confirmaExclusao = function (pesquisa, event) {
		confirm = $mdDialog.confirm()
							.title('Atenção')
							.textContent('Deseja realmente excluir a pesquisa?')
							.ok('Sim')
							.cancel('Não');
		$mdDialog.show( confirm )
					.then(function(){
						removerPesquisa(pesquisa, event);
					})
					.finally(function() {
						confirm = undefined;
					});
	}

  	/* se o registro estiver em modo de rascunho o mesmo é excluído permanentemente, caso contrario
       altera a situação para rascunho */
    function removerPesquisa(pesquisa) {
      //se situação igual a 1-Rascunho
      if (pesquisa.situacao.id === 1) {
        
        $scope.promessaLista = pesquisaService.deletar(pesquisa).success(
                function(data, status, headers, config) {
                  $scope.pesquisas = $scope.buscarMinhasPesquisas();

                  $scope.clearAlerts();
                  $scope.addAlert("Registro removido com sucesso.", "alert-success");
                })
                .error(function(data, status, headers, config) {
                  $scope.clearAlerts();
                  $scope.addAlert("Ocorreu um erro e não foi possível remover o registro.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
      } else {
        pesquisa.situacao.id = 1;
        
		var user = pesquisa.autor;
		if (user.perfilUsuario.id === 2) {
			delete user.turma;
		}
		pesquisa.autor = user;

        $scope.promessaLista = pesquisaService.atualizar(pesquisa).success(
                function(data, status, headers, config) {
                  $scope.pesquisas = $scope.buscarMinhasPesquisas();

                  $scope.clearAlerts();
                  $scope.addAlert("Registro atualizado com sucesso.", "alert-success");
                })
                .error(function(data, status, headers, config) {
                  $scope.clearAlerts();
                  $scope.addAlert("Ocorreu um erro e não foi possível atualizar a situação do registro para rascunho.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
      }
    }

    $scope.mostraBotaoPublicar = function(situacaoPesquisa) {
      //se situação igual a 1-Rascunho ou 2-Avaliação
      if ( situacaoPesquisa === 1 || (situacaoPesquisa === 2 && Auth.isProfessor())) {
        return true;
      }
      return false;
    }

    $scope.mostraBotaoRemover = function(autor, situacaoPesquisa) {
		  return pesquisaEhDoUsuarioLogado(autor) || (situacaoPesquisa === 3 && Auth.isProfessor());
    }

    function pesquisaEhDoUsuarioLogado(autor) {
    	var user = Auth.getUser();
      
		if (user.id === autor.id) {
			return true;
		}
		return false;
    }

    $scope.mostraFiltro = function() {
      //se situação igual a 1-Rascunho ou 2-Avaliação
      return Auth.isProfessor();
    }


    function buscarPerguntaPorPesquisa(idPesquisa) {
      $scope.promessaLista = perguntaService.buscarPerguntaPorPesquisa(idPesquisa)
                                              .success(function(data, status, headers, config) {
                                                $scope.pergunta = {};
                                                $scope.perguntas = data;
                                              }).error(function(data, status, headers, config) {
                                                if (data.error == undefined || data.error == null) {
                                                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar as perguntas.", "alert-warning");
                                                } else {
                                                  $scope.addAlert(data.error, "alert-warning");
                                                }
                                            });
    }

    function verPesquisaPorId(idPesquisa) {
      $scope.promessaLista = pesquisaService.verPesquisaPorId(idPesquisa)
                                              .success(function(data, status, headers, config) {
                                                $scope.pesquisasView = data;
                                              }).error(function(data, status, headers, config) {
                                                if (data.error == undefined || data.error == null) {
                                                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar a pesquisa.", "alert-warning");
                                                } else {
                                                  $scope.addAlert(data.error, "alert-warning");
                                                }
                                            });
    }

    /* inicializa a tela carregando todos as pesquisas cadastrados para o usuário logado */
    $scope.buscarMinhasPesquisas();

  $scope.verPesquisa = function(pesquisa, event) {
    $location.path('/ver_pesquisa/'+pesquisa.id); 
  }

  //trata a navegacao para visualização da pesquisa
  if ($routeParams.idPesquisaView != undefined) {
    console.log($routeParams.idPesquisaView);
    var idPesquisaView = $routeParams.idPesquisaView;
    verPesquisaPorId(idPesquisaView);
  }

  $scope.editarAvaliacao = function(pesquisa, event) {
    $location.path('/cadastro_avaliacao_pesquisa/pesquisa/'+pesquisa.id); 
  }

  $scope.confirmaExclusaoPergunta = function (pergunta, event) {
    confirm = $mdDialog.confirm()
              .title('Atenção')
              .textContent('Deseja realmente excluir a pergunta?')
              .ok('Sim')
              .cancel('Não');
    $mdDialog.show( confirm )
          .then(function(){
            removerPergunta(pergunta, event);
          })
          .finally(function() {
            confirm = undefined;
          });
  }
  

    function removerPergunta(pergunta, event) {
    
    $scope.promessaLista = perguntaService.deletar(pergunta)
                        .success(function(data, status, headers, config) {
                          
                          $scope.perguntas = buscarPerguntaPorPesquisa($scope.pesquisa.id);
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
