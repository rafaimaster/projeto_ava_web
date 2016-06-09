'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.blogService
 * @description
 * # blogService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('blogService', function ($http, CONSTANTES) {

	this.inserir = function(post) {
    return $http.post(CONSTANTES.URL_API_DEFAULT + "blog/", angular.toJson(post));
	};

	this.atualizar = function(post) {
    return $http.put(CONSTANTES.URL_API_DEFAULT + "blog/", angular.toJson(post));
	}

	this.deletar = function(post) { 
    return $http.delete(CONSTANTES.URL_API_DEFAULT + "blog/" + post.blogId); 
	}

	this.buscarMeusPosts = function(idUsuario) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'blog/meusPosts/' + idUsuario);
	}

	this.buscaPostsAlunos = function(idProfessor) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'blog/postsDosAlunos/' + idProfessor);
	}

  this.buscarTodos = function() {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'blog');
  }

  this.buscarPorId = function(idBlog) {
    return $http.get(CONSTANTES.URL_API_DEFAULT + 'blog/' + idBlog);
	}

  this.buscarPostsPublicados = function() {
      return $http.get(CONSTANTES.URL_API_DEFAULT + 'blog/publicados');
  }
});
