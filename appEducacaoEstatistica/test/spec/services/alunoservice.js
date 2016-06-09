'use strict';

describe('Service: alunoService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var alunoService;
  beforeEach(inject(function (_alunoService_) {
    alunoService = _alunoService_;
  }));

  it('should do something', function () {
    expect(!!alunoService).toBe(true);
  });

});
