'use strict';

describe('Controller: AlunoCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var AlunoCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AlunoCtrl = $controller('AlunoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AlunoCtrl.awesomeThings.length).toBe(3);
  });
});
