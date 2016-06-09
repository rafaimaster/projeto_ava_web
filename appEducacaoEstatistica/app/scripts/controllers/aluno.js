'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:AlunoCtrl
 * @description
 * # AlunoCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('AlunoCtrl', function ($scope, alunoService, turmaService, $routeParams, $mdDialog, $location) {
    

	$scope.usuario = {};
	$scope.turma = {};
  	$scope.alerts = {};
    $scope.sexo = {};
  	$scope.promessaFormulario = "";
  	$scope.promessaBuscaPerfil = "";
    $scope.valorConfirmacaoSenha = "";


    $scope.generos = [
      { id: '', nome: '-- Selecione --' },
      { id: 'F', nome: 'Feminino' },
      { id: 'M', nome: 'Masculino' }
    ];
    $scope.sexo = undefined;

  	/* =============== FUNÇÕES DE CONTROLE =============== */

 	//trata a navegacao para edicao
  if ($routeParams.idTurma != undefined) {

    var idTurma = $routeParams.idTurma;
    var idAluno = $routeParams.idAluno;

    if (idAluno === undefined) {
    	buscarTurma(idTurma);
    } else if (idAluno != undefined && idTurma != undefined) {
      buscarAluno(idTurma, idAluno);
    }
  		
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

    $scope.limparFormulario = function() {
    	$scope.clearAlerts();
    	
      if($scope.usuario.turma != undefined) {
        $location.path("/editar_turma/"+$scope.usuario.turma.id); 
      } else if ($scope.turma != undefined) {
        $location.path("/editar_turma/"+$scope.turma.id); 
      }
      $scope.usuario = {};
      $scope.sexo = undefined;
      $scope.valorConfirmacaoSenha = "";
    }

    $scope.validarSenha = function() {
      if ($scope.usuario != null && $scope.usuario.loginUsuario != null) {
        if ($scope.usuario.loginUsuario.senha === $scope.valorConfirmacaoSenha) {
          return true;
        }
      }
      return false;
    }

    /* =============== FUNÇÕES DE PERSISTENCIA =============== */
	function buscarTurma (id) {

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
    }

    function buscarAluno (idTurma, idAluno) {
      
      $scope.promessaLista = alunoService.buscarAluno(idTurma, idAluno)
                                              .success(function(data, status, headers, config) {
                                                $scope.aluno = {};
                                                $scope.usuario = data;
                                                $scope.usuario.dtNascimento = new Date(data.dtNascimento);
                                                $scope.usuario.dtCadastro = new Date(data.dtCadastro);
                                                $scope.sexo = { id: $scope.usuario.ieSexo };
                                              }).error(function(data, status, headers, config) {
                                                if (data.error == undefined || data.error == null) {
                                                  $scope.addAlert("Ocorreu um erro e não foi possível recuperar o aluno.", "alert-warning");
                                                } else {
                                                  $scope.addAlert(data.error, "alert-warning");
                                                }
                                              });
    }

  	$scope.addUsuario = function() {

        $scope.usuario.situacao = true;
        
        var sexo = angular.fromJson( $scope.sexo);
        $scope.usuario.ieSexo = sexo.id;

        if (!$scope.validarSenha()) {
          $scope.clearAlerts();
          $scope.addAlert("A senha e a confirmação de senha não são iguais.", "alert-warning");
        } else {

          if ($scope.usuario.id == null || $scope.usuario.id === undefined) {
            $scope.usuario.turma = $scope.turma;
            $scope.promessaFormulario = alunoService.inserir($scope.usuario)
                                                        .success(
                                                        function(data, status, headers, config) {
                                                        	$scope.limparFormulario();

                                                        	$scope.addAlert("Usuário criado com sucesso.", "alert-success");
                                                        }).error(function(data, status, headers, config) {
                                                        	$scope.clearAlerts();
                                                        	//$scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
                                                        	$scope.addAlert(data.error, "alert-warning");

                                                        });
          } else {
             $scope.promessaFormulario = alunoService.atualizar($scope.usuario)
                                                          .success(
                                                            function(data, status, headers, config) {

                                                            $scope.clearAlerts();
                                                            $scope.addAlert("Usuário atualizado com sucesso.", "alert-success");
                                                          }).error(function(data, status, headers, config) {
                                                            $scope.clearAlerts();
                                                            $scope.addAlert(data.error, "alert-warning");

                                                          });
          }
        }
      }

});
