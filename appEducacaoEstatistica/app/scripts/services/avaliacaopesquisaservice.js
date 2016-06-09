'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.AvaliacaoPesquisaService
 * @description
 * # AvaliacaoPesquisaService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('avaliacaoPesquisaService', function ($http, CONSTANTES) {

  	this.buscarPorId = function(idAvaliacaoPesquisa) {
    	return $http.get(CONSTANTES.URL_API_DEFAULT + 'avaliacaoPesquisa/' + idAvaliacaoPesquisa);
	}

    this.inserir = function(avaliacaoPesquisa) {
    	return $http.post(CONSTANTES.URL_API_DEFAULT + "avaliacaoPesquisa", angular.toJson(avaliacaoPesquisa));
	};

	this.atualizar = function(avaliacaoPesquisa) {
    	return $http.put(CONSTANTES.URL_API_DEFAULT + "avaliacaoPesquisa", angular.toJson(avaliacaoPesquisa));
	}

	this.deletar = function(avaliacaoPesquisa) {
    	return $http.delete(CONSTANTES.URL_API_DEFAULT + "avaliacaoPesquisa/" + avaliacaoPesquisa.id); 
	}

	/* recupera avaliação pelo código da pesquisa */
	this.buscarPorIdPesquisa = function(idPesquisaAvaliacao) {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'avaliacaoPesquisa/pesquisa/' + idPesquisaAvaliacao);
	}

});
