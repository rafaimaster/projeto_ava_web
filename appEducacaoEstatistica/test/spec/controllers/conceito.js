'use strict';

describe('Controller: ConceitoCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var ConceitoCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ConceitoCtrl = $controller('ConceitoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ConceitoCtrl.awesomeThings.length).toBe(3);
  });
});
