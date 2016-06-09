'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.alunoService
 * @description
 * # alunoService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('alunoService', function ($http, CONSTANTES) {
    
	this.inserir = function(aluno) {
	    //console.log(aluno);
	    return $http.post(CONSTANTES.URL_API_DEFAULT + "aluno/", angular.toJson(aluno));
	};

	this.atualizar = function(aluno) {
    	return $http.put(CONSTANTES.URL_API_DEFAULT + "aluno/", angular.toJson(aluno));
	}

	this.buscar = function(aluno) {
    	return $http.get(CONSTANTES.URL_API_DEFAULT + 'aluno');
	}

	this.buscarAlunosPorTurma = function(idTurma) {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'aluno/turma/' + idTurma);
	}

	this.buscarAluno = function(idTurma, idAluno) {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'aluno/' + idAluno + '/turma/' + idTurma);
	}

	this.deletar = function(aluno) {
		return $http.delete(CONSTANTES.URL_API_DEFAULT + "aluno/" + aluno.id); 
	}

});
