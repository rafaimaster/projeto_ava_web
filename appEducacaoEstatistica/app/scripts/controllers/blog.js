'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:BlogCtrl
 * @description
 * # BlogCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('BlogCtrl', function ($scope, $location, $routeParams, $mdDialog, blogService, CONSTANTES, Auth) {
    
  $scope.post = {};
    $scope.postagens = [];
    $scope.alerts = {};
    $scope.promessaLista = "";
    $scope.promessaFormulario = "";
   

    /* =============== FUNÇÕES DE CONTROLE =============== */
    $scope.editBlog = function(post, event) {
    	if (postEhDoUsuarioLogado(post.autor)) {
    		$location.path('/editar_blog/'+post.blogId);
    	} else {
    		alert = $mdDialog.confirm()
							.title('Alerta')
							.textContent('Não é permitido editar o post do aluno!')
							.ok('Ok');
			$mdDialog.show( alert )
						.then(function(){
							
						})
						.finally(function() {
							alert = undefined;
						});
    	}
    }

    $scope.verPost = function(post, event) {
    	$location.path('/ver_blog/'+post.blogId); 
    }

	function verBlog(idBlog) {
    	$scope.post = {};
    	$scope.promessaLista = blogService.buscarPorId(idBlog)
    											.success(function(data, status, headers, config) {
		      					                	$scope.post = {};
		      					                	$scope.post = data;
                					}).error(function(data, status, headers, config) {
					                	if (data.error == undefined || data.error == null) {
														  $scope.addAlert("Ocorreu um erro e não foi possível recuperar as postagens cadastradas.", "alert-warning");
  													} else {
  														$scope.addAlert(data.error, "alert-warning");
  													}
                					});
    }

	//trata a navegacao para edicao
	if ($routeParams.idBlog != undefined) {
		var idBlog = $routeParams.idBlog;
		verBlog(idBlog);
	}


	$scope.viewListPost = function() {
		$scope.post = {};
		$scope.postagens = [];
    	$scope.postagens = $scope.buscarMeusPosts();

		//redireciona para a pagina de lista
		$location.path("/lista_blog"); 
    }

    $scope.addAlert = function(message, type) {
      //$scope.clearAlerts();
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
       $scope.promessaLista = blogService.buscarTodos().success(
                function(data, status, headers, config) {
                  $scope.postagens = [];
                  $scope.postagens= data;
                }).error(function(data, status, headers, config) {
                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
    }


    $scope.buscarMeusPosts =  function() {
       $scope.promessaLista = blogService.buscarMeusPosts(Auth.getUser().id).success(
                function(data, status, headers, config) {
                  $scope.postagens = [];
                  $scope.postagens= data;
                }).error(function(data, status, headers, config) {
                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
    }
    
    $scope.buscaPostsAlunos =  function() {
    	var user = Auth.getUser();

      $scope.promessaLista = blogService.buscaPostsAlunos(user.id).success(
                function(data, status, headers, config) {
                  $scope.postagens = [];
                  $scope.postagens= data;
                }).error(function(data, status, headers, config) {
                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar os registros cadastrados para os alunos.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
    }


    /* =============== FUNÇÕES DE PERSISTENCIA =============== */

  	$scope.addPost = function() {

		if ($scope.post.blogId == null || $scope.post.blogId === undefined) {

			var user = Auth.getUser();

			if (user.perfilUsuario.id === 2) {
				delete user.turma;
			}

			$scope.post.autor = user;



			$scope.promessaFormulario = blogService.inserir($scope.post).success(
																			function(data, status, headers, config) {
		                                                                      $scope.viewListPost();
		                                                                      $scope.clearAlerts(); 
		                                                                      $scope.addAlert("Registro inserido com sucesso.", "alert-success");
		                                                                    }).error(function(data, status, headers, config) {
		                                                                      $scope.clearAlerts();
		                                                                      $scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
		                                                                      $scope.addAlert(data.error, "alert-warning");
		                                                                    });
		} else {
			var user = $scope.post.autor;

			if (user.perfilUsuario.id === 2) {
				delete user.turma;
			}

			$scope.post.autor = user;
			$scope.promessaFormulario = blogService.atualizar($scope.post).then(
												        function success(response) {
												          $scope.viewListPost();
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

  	$scope.confirmaPublicacao = function (post, event) {
		confirm = $mdDialog.confirm()
							.title('Atenção')
							.textContent('Deseja realmente publicar o post?')
							.ok('Sim')
							.cancel('Não');
		$mdDialog.show( confirm )
					.then(function(){
						publicar(post, event);
					})
					.finally(function() {
						confirm = undefined;
					});
	}

  	function publicar(post, event) {

      /* trata a situação da publicação do post conforme o perfil do usuário */
      if (Auth.isProfessor()) {
        post.situacaoBlog.id = 3;//3-Publicado
      } else {
        post.situacaoBlog.id = 2;//2-Avaliação
      }

      var user = post.autor;

		if (user.perfilUsuario.id === 2) {
			delete user.turma;
		}

		post.autor = user;

      $scope.promessaLista = blogService.atualizar(post).success(
                function(data, status, headers, config) {
                  $scope.viewListPost();

                  $scope.clearAlerts();
                  $scope.addAlert("Registro publicado com sucesso.", "alert-success");
                })
                .error(function(data, status, headers, config) {
                  $scope.viewListPost();

                  $scope.clearAlerts();
                  $scope.addAlert("Ocorreu um erro e não foi possível atualizar o registro.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
  	}

  	$scope.confirmaExclusao = function (post, event) {
		confirm = $mdDialog.confirm()
							.title('Atenção')
							.textContent('Deseja realmente excluir o blog?')
							.ok('Sim')
							.cancel('Não');
		$mdDialog.show( confirm )
					.then(function(){
						removerPost(post, event);
					})
					.finally(function() {
						confirm = undefined;
					});
	}

  	/* se o registro estiver em modo de rascunho o mesmo é excluído permanentemente, caso contrario
       altera a situação para rascunho */
    function removerPost(post) {
      //se situação igual a 1-Rascunho
      if (post.situacaoBlog.id === 1) {
        
        $scope.promessaLista = blogService.deletar(post).success(
                function(data, status, headers, config) {
                  $scope.postagens = $scope.buscarMeusPosts();

                  $scope.clearAlerts();
                  $scope.addAlert("Registro removido com sucesso.", "alert-success");
                })
                .error(function(data, status, headers, config) {
                  $scope.clearAlerts();
                  $scope.addAlert("Ocorreu um erro e não foi possível remover o registro.", "alert-warning");
                  $scope.addAlert(data.error, "alert-warning");
                });
      } else {
        post.situacaoBlog.id = 1;
        
		var user = post.autor;
		if (user.perfilUsuario.id === 2) {
			delete user.turma;
		}
		post.autor = user;

        $scope.promessaLista = blogService.atualizar(post).success(
                function(data, status, headers, config) {
                  $scope.postagens = $scope.buscarMeusPosts();

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

    $scope.mostraBotaoPublicar = function(situacaoPost) {
      //se situação igual a 1-Rascunho ou 2-Avaliação
      if ( situacaoPost === 1 || (situacaoPost === 2 && Auth.isProfessor())) {
        return true;
      }
      return false;
    }

    $scope.mostraBotaoRemover = function(autor, situacaoPesquisa) {
		  return postEhDoUsuarioLogado(autor) || (situacaoPesquisa === 3 && Auth.isProfessor());
    }

    function postEhDoUsuarioLogado(autor) {
    	var user = Auth.getUser();
      
  		if (user.id  === autor.id) {
  			return true;
  		}
  		return false;
    }

    $scope.mostraFiltro = function() {
      //se situação igual a 1-Rascunho ou 2-Avaliação
      return Auth.isProfessor();
    }

    /* inicializa a tela carregando todos os blogs cadastrados para o usuário logado */
    if ("/lista_blog" == $location.path()) {
      $scope.buscarMeusPosts();
    }





  });
