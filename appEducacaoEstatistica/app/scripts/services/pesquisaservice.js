'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.pesquisaService
 * @description
 * # pesquisaService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('pesquisaService', function ($http, CONSTANTES) {
    
	this.inserir = function(pesquisa) {
    return $http.post(CONSTANTES.URL_API_DEFAULT + "pesquisa/", angular.toJson(pesquisa));
	};

	this.atualizar = function(pesquisa) {
    return $http.put(CONSTANTES.URL_API_DEFAULT + "pesquisa/", angular.toJson(pesquisa));
	}

	this.deletar = function(pesquisa) { 
    return $http.delete(CONSTANTES.URL_API_DEFAULT + "pesquisa/" + pesquisa.id); 
	}

	this.buscarPorId = function(id) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/' + id);
	}

	this.buscarMinhasPesquisas = function(idUsuario) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/minhasPesquisas/' + idUsuario);
	}

	this.buscarPesquisasDosAlunos = function(idProfessor) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/pesquisasDosAlunos/' + idProfessor);
	}

  this.buscarTodos = function() {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa');
  }

  this.verPesquisaPorId = function(idPesquisa) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/ver/' + idPesquisa);
  }

  this.buscarPesquisasPublicadas = function() {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/publicadas');
  }

  this.buscarPesquisasEncerradas = function() {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/encerradas');
  }

  this.buscarPesquisaPorIdParaResponder = function(idPesquisaResposta) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/verPerguntas/' + idPesquisaResposta);
  }

  this.responderPesquisa = function(respostas) {
    return $http.post(CONSTANTES.URL_API_DEFAULT + "pesquisa/responder/", angular.toJson(respostas));
  };

  this.buscarPesquisasChart = function(idPergunta1, idPergunta2) {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'pesquisa/chart/'+idPergunta1+'/'+idPergunta2);
  };

});
