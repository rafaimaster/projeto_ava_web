'use strict';

describe('Controller: UsuarioctrlCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var UsuarioctrlCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UsuarioctrlCtrl = $controller('UsuarioctrlCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(UsuarioctrlCtrl.awesomeThings.length).toBe(3);
  });
});
