'use strict';

/**
 * @ngdoc overview
 * @name appEducacaoEstatisticaApp
 * @description
 * # appEducacaoEstatisticaApp
 *
 * Main module of the application.
 */

 var appModule = angular.module('appEducacaoEstatisticaApp', [
                                'ngAnimate',
                                'ngAria',
                                'ngCookies',
                                'ngMessages',
                                'ngResource',
                                'ngRoute',
                                'ngSanitize',
                                'ngMaterial',
                                'ngStorage',
                                'textAngular',
                                'chart.js',
                                'mdPickers'
                              ]);


appModule.config(function($mdDateLocaleProvider) {
  // Example of a French localization.
  $mdDateLocaleProvider.months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
  $mdDateLocaleProvider.shortMonths = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
  //$mdDateLocaleProvider.days = ['dimanche', 'lundi', 'mardi', ...];
  //$mdDateLocaleProvider.shortDays = ['Di', 'Lu', 'Ma', ...];
  // Can change week display to start on Monday.
  //$mdDateLocaleProvider.firstDayOfWeek = 1;
  // Optional.
  //$mdDateLocaleProvider.dates = [1, 2, 3, 4, 5, 6, ...];
  // Example uses moment.js to parse and format dates.
  /**/
 
  
 
  // In addition to date display, date components also need localized messages
  // for aria-labels for screen-reader users.
  $mdDateLocaleProvider.weekNumberFormatter = function(weekNumber) {
    return 'Semana ' + weekNumber;
  };
  $mdDateLocaleProvider.msgCalendar = 'Calendário';
  //$mdDateLocaleProvider.msgOpenCalendar = 'Ouvrir le calendrier';
});


appModule.config(function ($routeProvider, $httpProvider, $provide) {

  $httpProvider.interceptors.push('sessionInjector');
  $httpProvider.interceptors.push('sessionRecoverer');

  $provide.decorator('taOptions',  ['$delegate', function(taOptions) { // $delegate is the taOptions we are decorating
    taOptions.toolbar = [
                          ['h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'pre', 'quote'],
                          ['bold', 'italics', 'underline', 'strikeThrough', 'ul', 'ol', 'redo', 'undo', 'clear'],
                          ['justifyLeft', 'justifyCenter', 'justifyRight', 'indent', 'outdent'],
                          ['html', 'insertImage','insertLink', 'insertVideo']
                        ];
    return taOptions;
  }]);

  $provide.decorator('taTranslations', function($delegate) {
    $delegate.heading.tooltip = 'Título';
    $delegate.p.tooltip = 'Parágrafo';
    $delegate.pre.tooltip = 'Texto pré-formatado';
    $delegate.quote.tooltip = 'Citação do parágrafo selecionado';
    $delegate.strikeThrough.tooltip = 'Tachado';
    $delegate.ul.tooltip = 'Lista';
    $delegate.ol.tooltip = 'Lista numerada';
    $delegate.undo.tooltip = 'Desfazer';
    $delegate.redo.tooltip = 'Refazer';
    $delegate.indent.tooltip = 'Aumentar recuo';
    $delegate.outdent.tooltip = 'Diminuir recuo';
    $delegate.html.tooltip = 'Ver código HTML';
    $delegate.justifyLeft.tooltip = 'Alinhar à esquerda';
    $delegate.justifyCenter.tooltip = 'Centralizar';
    $delegate.justifyRight.tooltip = 'Alinhar à direita';
    $delegate.bold.tooltip = 'Negrito';
    $delegate.italic.tooltip = 'Itálico';
    $delegate.underline.tooltip = 'Sublinhar';
    $delegate.insertLink.tooltip = 'Inserir Link';
    $delegate.insertLink.dialogPrompt = "Inserir URL";
    $delegate.editLink.targetToggle.buttontext = "Editar Ling";
    $delegate.editLink.reLinkButton.tooltip = "Rediger link";
    $delegate.editLink.unLinkButton.tooltip = "Fjern link";
    $delegate.insertVideo.tooltip = 'Inserir vídeo';
    $delegate.insertVideo.dialogPrompt = 'Inserir URL do vídeo';
    $delegate.clear.tooltip = 'Limpar';
    return $delegate;
  });

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main',
        auth: false
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about',
        auth: false
      })
      .when('/forum', {
        templateUrl: 'views/forum.html',
        controller: 'AboutCtrl',
        controllerAs: 'about',
        auth: false
      })
      .when('/plano_aula', {
        templateUrl: 'views/plano_aula.html',
        controller: '',
        controllerAs: '',
        auth: false
      })
      .when('/formacao_continuada', {
        templateUrl: 'views/formacao_continuada.html',
        controller: '',
        controllerAs: '',
        auth: false
      })
      .when('/conceito', {
        templateUrl: 'views/conceito.html',
        controller: 'ConceitoCtrl',
        controllerAs: 'conceitoCtrl',
        auth: false
      })
      .when('/aprenda_estatistica', {
        templateUrl: 'views/aprenda_estatistica.html',
        controller: '',
        controllerAs: '',
        auth: false
      })
      .when('/aprenda_media', {
        templateUrl: 'views/aprenda_media.html',
        controller: '',
        controllerAs: '',
        auth: false
      })
      .when('/pesquisa', {
        templateUrl: 'views/pesquisa.html',
        controller: 'PesquisageralCtrl',
        controllerAs: 'pesquisageral',
        auth: false
      })
      .when('/blog', {
        templateUrl: 'views/blog.html',
        controller: 'BloggeralCtrl',
        controllerAs: 'bloggeral',
        auth: false
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        auth: false
      })
      .when('/cadastro_usuario', {
        templateUrl: 'views/cadastro_usuario.html',
        controller: 'UsuarioCtrl',
        controllerAs: 'usuarioCtrl',
        auth: false
      })
      .when('/cadastro_turma', {
        templateUrl: 'views/cadastro_turma.html',
        controller: 'TurmaCtrl',
        controllerAs: 'turmaCtrl',
        auth: true
      })
      .when('/editar_turma/:id', {
        templateUrl: 'views/cadastro_turma.html',
        controller: 'TurmaCtrl',
        controllerAs: 'turmaCtrl',
        auth: true
      })
      .when('/lista_turma', {
        templateUrl: 'views/lista_turma.html',
        controller: 'TurmaCtrl',
        controllerAs: 'turmaCtrl',
        auth: true
      })
      .when('/cadastro_aluno/:idTurma', {
        templateUrl: 'views/cadastro_aluno.html',
        controller: 'AlunoCtrl',
        controllerAs: 'alunoCtrl',
        auth: true
      })
      .when('/cadastro_aluno/turma/:idTurma/aluno/:idAluno', {
        templateUrl: 'views/cadastro_aluno.html',
        controller: 'AlunoCtrl',
        controllerAs: 'alunoCtrl',
        auth: true
      })
      .when('/cadastro_blog', {
        templateUrl: 'views/cadastro_blog.html',
        controller: 'BlogCtrl',
        controllerAs: 'blogCtrl',
        auth: true
      })
      .when('/lista_blog', {
        templateUrl: 'views/lista_blog.html',
        controller: 'BlogCtrl',
        controllerAs: 'blogCtrl',
        auth: true
      })
      .when('/editar_blog/:idBlog', {
        templateUrl: 'views/cadastro_blog.html',
        controller: 'BlogCtrl',
        controllerAs: 'blogCtrl',
        auth: true
      })
      .when('/ver_blog/:idBlog', {
        templateUrl: 'views/ver_blog.html',
        controller: 'BlogCtrl',
        controllerAs: 'blogCtrl',
        auth: true
      })

      .when('/cadastro_pesquisa', {
        templateUrl: 'views/cadastro_pesquisa.html',
        controller: 'PesquisaCtrl',
        controllerAs: 'pesquisaCtrl',
        auth: true
      })
      .when('/lista_pesquisa', {
        templateUrl: 'views/lista_pesquisa.html',
        controller: 'PesquisaCtrl',
        controllerAs: 'pesquisaCtrl',
        auth: true
      })
      .when('/editar_pesquisa/:id', {
        templateUrl: 'views/cadastro_pesquisa.html',
        controller: 'PesquisaCtrl',
        controllerAs: 'pesquisaCtrl',
        auth: true
      })
      .when('/ver_pesquisa/:idPesquisaView', {
        templateUrl: 'views/ver_pesquisa.html',
        controller: 'PesquisaCtrl',
        controllerAs: 'pesquisaCtrl',
        auth: true
      })
      .when('/responder_pesquisa/:idPesquisaResposta', {
        templateUrl: 'views/responder_pesquisa.html',
        controller: 'ResponderpesquisaCtrl',
        controllerAs: 'responderpesquisa',
        auth: true
      })
      .when('/cadastro_pergunta/pesquisa/:idPesquisa', {
        templateUrl: 'views/cadastro_pergunta.html',
        controller: 'PerguntaCtrl',
        controllerAs: 'perguntaCtrl',
        auth: true
      })
      .when('/cadastro_pergunta/pesquisa/:idPesquisa/pergunta/:idPergunta', {
        templateUrl: 'views/cadastro_pergunta.html',
        controller: 'PerguntaCtrl',
        controllerAs: 'perguntaCtrl',
        auth: true
      })
      .when('/cadastro_avaliacao_pesquisa/pesquisa/:idPesquisaAvaliacao', {
        templateUrl: 'views/cadastro_avaliacao_pesquisa.html',
        controller: 'AvaliacaopesquisaCtrl',
        controllerAs: 'avaliacaopesquisaCtrl',
        auth: true
      })
      .when('/avaliacao_pesquisa/pesquisa/:idPesquisaAvaliacao', {
        templateUrl: 'views/avaliacao_pesquisa.html',
        controller: 'AvaliacaopesquisageralCtrl',
        controllerAs: 'avaliacaopesquisageralCtrl',
        auth: true
      })
      .otherwise({
        redirectTo: '/'
      });
  });


  appModule.constant("CONSTANTES", {
        "PAGE_CONTROLE_BLOG": "1",
        "PAGE_CONTROLE_PESQUISA": "2",
        "PAGE_CONTROLE_PLANO_AULA": "3",
        "PAGE_CONTROLE_FORUM": "4",
        "PAGE_CONTROLE_TURMA": "5",
        "ACAO_VISUALICACAO": "0",
        "ACAO_INCLUSAO": "1",
        "ACAO_ALTERACAO": "2",
        "ACAO_EXCLUSAO": "3",
        "URL_API_DEFAULT" : "http://localhost:8080/APIAva/api/"
        //"http://apiava.jelasticlw.com.br/APIAva/api/"
    });


appModule.factory('Auth', function ($sessionStorage) {
    return{
        setUser : function(usuario, isLogado){
            $sessionStorage.usuarioLogado = usuario;
            $sessionStorage.isLogado = isLogado;
        },
        getUser : function() {
          return ($sessionStorage.usuarioLogado) ? $sessionStorage.usuarioLogado : null;
        },
        isLogado : function(){
          return ($sessionStorage.isLogado) ? $sessionStorage.isLogado : false;
        },
        isProfessor : function(){
          var result = false;
          var usuario = $sessionStorage.usuarioLogado;
          var perfilUsuario = {};

          if (usuario != undefined && usuario != null) {
            perfilUsuario = usuario.perfilUsuario;
            result = (perfilUsuario.id == 1);
          }

          return result;
        }
    }
});

appModule.factory('sessionInjector', function(Auth, $q) { 
    var usuario = {};
    var loginUsuario = {};
    var sessionInjector = {
        request: function(config) {
          
          if (Auth.isLogado()) {
            usuario = Auth.getUser();
            if (usuario != null) {
              loginUsuario  = usuario.loginUsuario;
              config.headers['Authorization'] = 'Bearer ' + loginUsuario.token;
            }
          }
          return config;    
        }

    };
    return sessionInjector;
});


appModule.factory('sessionRecoverer', ['$q', '$injector','$location', '$sessionStorage', function($q, $injector, $location, $sessionStorage) {  
    var sessionRecoverer = {
        responseError: function(response) {
            // Session has expired
            if (response.status == 401){
               // var SessionService = $injector.get('SessionService');
               // var $http = $injector.get('$http');
                var deferred = $q.defer();

                delete $sessionStorage.usuarioLogado;
                delete $sessionStorage.isLogado;
                $location.path("/login");
                // Create a new session (recover the session)
                // We use login method that logs the user in using the current credentials and
                // returns a promise
                //SessionService.login().then(deferred.resolve, deferred.reject);

                // When the session recovered, make the same backend call again and chain the request
                //return deferred.promise.then(function() {
                 //   return $http(response.config);
                //});
                return $q.reject(response);
            }
            return $q.reject(response);
        }
    };
    return sessionRecoverer;
}]);

appModule.run(function($location, $log, $rootScope, $route, Auth) {
  //$location.search('foo', 'bar').path('/secure');
  $rootScope.$on('$locationChangeStart', function(ev, next, current) {
    var nextPath = $location.path(),
    nextRoute = $route.routes[nextPath];
    //$log.info(nextRoute);
    //$log.info(nextRoute.auth);
    //$log.info(Auth.isLogado());

    if (nextRoute && nextRoute.auth && !Auth.isLogado()) {
      $location.path("/login");
    }
  });
});