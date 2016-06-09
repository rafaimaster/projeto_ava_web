'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.professorService
 * @description
 * # professorService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('professorService', function ($http, CONSTANTES) {
    
	this.inserir = function(professor) {
		return $http.post(CONSTANTES.URL_API_DEFAULT + "professor/", angular.toJson(professor));
	};

	this.atualizar = function(professor) {
		return $http.put(CONSTANTES.URL_API_DEFAULT + "professor/", angular.toJson(professor));
	}

	this.buscar = function(professor) {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'professor');
	}

	this.buscarPerfisUsuarioAtivo = function() {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'perfilUsuario/ativos');
	}

	this.deletar = function(professor) {
		return $http.delete(CONSTANTES.URL_API_DEFAULT + "professor"+ professor.id); 
	}

});
