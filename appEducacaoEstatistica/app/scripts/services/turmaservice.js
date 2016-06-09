'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.turmaService
 * @description
 * # turmaService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('turmaService', function ($http, CONSTANTES) {
    
	this.inserir = function(turma) {
    var anoTurma = angular.fromJson(turma.anoTurma);
    turma.anoTurma = anoTurma;

      return $http.post(CONSTANTES.URL_API_DEFAULT + "turma", angular.toJson(turma));
  	};

  	this.atualizar = function(turma) {
      return $http.put(CONSTANTES.URL_API_DEFAULT + "turma", angular.toJson(turma));
  	}

  	this.deletar = function(indice, turma) {
      return $http.delete(CONSTANTES.URL_API_DEFAULT + "turma/" + turma.id); 
  	}

  	/* recupera apenas as turmas vinculadas ao cadastro do usuário */
  	this.buscar = function(idUsuario) {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'turma/minhasTurmas/' + idUsuario);
	}

	this.buscarPorId = function(idTurma) {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'turma/' + idTurma);
	}

});
