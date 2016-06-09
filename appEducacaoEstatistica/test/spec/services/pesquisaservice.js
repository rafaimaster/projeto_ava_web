'use strict';

describe('Service: pesquisaService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var pesquisaService;
  beforeEach(inject(function (_pesquisaService_) {
    pesquisaService = _pesquisaService_;
  }));

  it('should do something', function () {
    expect(!!pesquisaService).toBe(true);
  });

});
