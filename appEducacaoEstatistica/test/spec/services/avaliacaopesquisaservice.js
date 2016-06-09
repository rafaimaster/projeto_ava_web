'use strict';

describe('Service: AvaliacaoPesquisaService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var AvaliacaoPesquisaService;
  beforeEach(inject(function (_AvaliacaoPesquisaService_) {
    AvaliacaoPesquisaService = _AvaliacaoPesquisaService_;
  }));

  it('should do something', function () {
    expect(!!AvaliacaoPesquisaService).toBe(true);
  });

});
