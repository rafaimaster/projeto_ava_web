'use strict';

/**
 * @ngdoc function
 * @name appEducacaoEstatisticaApp.controller:UsuarioctrlCtrl
 * @description
 * # UsuarioctrlCtrl
 * Controller of the appEducacaoEstatisticaApp
 */
angular.module('appEducacaoEstatisticaApp')
  .controller('UsuarioCtrl', function ($scope, $location,  professorService, $mdpDatePicker) {
  
	$scope.usuario = {};
  	$scope.perfisUsuario = [];
  	$scope.indexPerfilUsuario = {};
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
    	$scope.usuario = {};
      $scope.sexo = undefined;
     	$scope.valorConfirmacaoSenha = "";
    	$scope.indexPerfilUsuario = {};
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
  	$scope.addUsuario = function() {
        $scope.usuario.situacao = true;
        
        var sexo = angular.fromJson( $scope.sexo);
        $scope.usuario.ieSexo = sexo.id;
        //usuario.loginUsuario.senha = undefined;
        //usuario.loginUsuario.token = undefined;
        //usuario.loginUsuario.usuario = $scope.usuario;
        if (!$scope.validarSenha()) {
          $scope.clearAlerts();
          $scope.addAlert("A senha e a confirmação de senha não são iguais.", "alert-warning");
        } else {
          $scope.promessaFormulario = professorService.inserir($scope.usuario)
                  .success(
                  function(data, status, headers, config) {
                  	//$scope.limparFormulario();

                  	//$scope.addAlert("Usuário criado com sucesso.", "alert-success");

                    $location.path("/login"); 
                  }).error(function(data, status, headers, config) {
                  	$scope.clearAlerts();
                  	//$scope.addAlert("Ocorreu um erro e não foi possível gravar o registro.", "alert-warning");
                  	$scope.addAlert(data.error, "alert-warning");

                  });
        }
      }

      $scope.buscarPerfisUsuarioAtivo = function() {
      	$scope.promessaBuscaPerfil = professorService.buscarPerfisUsuarioAtivo().success(
                function(data, status, headers, config) {
                  $scope.perfisUsuario = angular.copy(data);
                }).error(function(data, status, headers, config) {
                	$scope.clearAlerts();
                	$scope.addAlert("Ocorreu um erro e não foi possível recuperar a lista de perfis.", "alert-warning");
                	$scope.addAlert(data.error, "alert-warning");
                });
      }

  });
