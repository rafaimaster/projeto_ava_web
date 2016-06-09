'use strict';

describe('Controller: ResponderpesquisaCtrl', function () {

  // load the controller's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  var ResponderpesquisaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ResponderpesquisaCtrl = $controller('ResponderpesquisaCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ResponderpesquisaCtrl.awesomeThings.length).toBe(3);
  });
});
