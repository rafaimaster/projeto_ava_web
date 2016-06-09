'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.perguntaSservice
 * @description
 * # perguntaSservice
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('perguntaService', function ($http, CONSTANTES) {

  this.inserir = function(pergunta) {
    return $http.post(CONSTANTES.URL_API_DEFAULT + "pergunta", angular.toJson(pergunta));
	};

	this.atualizar = function(pergunta) {
    return $http.put(CONSTANTES.URL_API_DEFAULT + "pergunta", angular.toJson(pergunta));
	}

	this.deletar = function(pergunta) {
    return $http.delete(CONSTANTES.URL_API_DEFAULT + "pergunta/" + pergunta.id); 
	}

	/* recupera apenas as turmas vinculadas ao cadastro do usuário */
	this.buscarPerguntaPorPesquisa = function(idPesquisa) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pergunta/pesquisa/' + idPesquisa);
  }

	this.buscarPorId = function(idPergunta) {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'pergunta/' + idPergunta);
	}

  this.buscarPergunta = function(idPesquisa, idPergunta) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pergunta/' + idPergunta + '/pesquisa/' + idPesquisa);
  }

});
