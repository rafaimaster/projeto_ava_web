'use strict';

describe('Controller: AvaliacaopesquisaCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var AvaliacaopesquisaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AvaliacaopesquisaCtrl = $controller('AvaliacaopesquisaCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AvaliacaopesquisaCtrl.awesomeThings.length).toBe(3);
  });
});
