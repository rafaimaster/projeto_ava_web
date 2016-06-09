'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.alternativaService
 * @description
 * # alternativaService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('alternativaService', function ($http, CONSTANTES) {
    
	this.inserir = function(alternativa) {
    	return $http.post(CONSTANTES.URL_API_DEFAULT + "alternativa", angular.toJson(alternativa));
	};

	this.atualizar = function(alternativa) {
    	return $http.put(CONSTANTES.URL_API_DEFAULT + "alternativa", angular.toJson(alternativa));
	}

	this.deletar = function(alternativa) {
    	return $http.delete(CONSTANTES.URL_API_DEFAULT + "alternativa/" + alternativa.id); 
	}

	/* recupera apenas as turmas vinculadas ao cadastro do usuário */
	this.buscarAlternativaPorPergunta = function(idPergunta) {
	    return $http.get(CONSTANTES.URL_API_DEFAULT + 'alternativa/pergunta/' + idPergunta);
	}

	this.buscarPorId = function(idAlternativa) {
    	return $http.get(CONSTANTES.URL_API_DEFAULT + 'alternativa/' + idAlternativa);
	}

	this.buscarPergunta = function(idPergunta, idAlternativa) {
    	return $http.get(CONSTANTES.URL_API_DEFAULT + 'alternativa/' + idAlternativa + '/pergunta/' + idPergunta);
	}

});
