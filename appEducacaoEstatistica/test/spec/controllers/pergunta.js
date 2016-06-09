'use strict';

describe('Controller: PerguntaCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var PerguntaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PerguntaCtrl = $controller('PerguntaCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(PerguntaCtrl.awesomeThings.length).toBe(3);
  });
});
