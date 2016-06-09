'use strict';

describe('Controller: AvaliacaopesquisageralCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var AvaliacaopesquisageralCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AvaliacaopesquisageralCtrl = $controller('AvaliacaopesquisageralCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AvaliacaopesquisageralCtrl.awesomeThings.length).toBe(3);
  });
});
