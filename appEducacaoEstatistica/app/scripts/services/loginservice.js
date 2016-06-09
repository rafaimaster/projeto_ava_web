'use strict';

/**
 * @ngdoc service
 * @name educacaoestatisticaApp.loginService
 * @description
 * # loginService
 * Service in the educacaoestatisticaApp.
 */
angular.module('appEducacaoEstatisticaApp')
  .service('loginService', function ($http, CONSTANTES) {
    
	this.login = function(loginUsuario) {
      return $http.post(CONSTANTES.URL_API_DEFAULT + "loginUsuario/login", angular.toJson(loginUsuario));
  	};

	this.logout = function(loginUsuario) {
      return $http.post(CONSTANTES.URL_API_DEFAULT + "loginUsuario/logout", angular.toJson(loginUsuario));
  	};

  });
