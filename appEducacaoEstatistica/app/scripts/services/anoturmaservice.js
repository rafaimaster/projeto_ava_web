'use strict';

/**
 * @ngdoc service
 * @name appEducacaoEstatisticaApp.anoTurmaService
 * @description
 * # anoTurmaService
 * Service in the appEducacaoEstatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('anoTurmaService', function ($http, CONSTANTES) {

    this.buscarAnosTurmaAtivos = function() {
		return $http.get(CONSTANTES.URL_API_DEFAULT + 'anoTurma/ativos');
	}

  });
