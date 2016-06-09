'use strict';

describe('Service: anoTurmaService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var anoTurmaService;
  beforeEach(inject(function (_anoTurmaService_) {
    anoTurmaService = _anoTurmaService_;
  }));

  it('should do something', function () {
    expect(!!anoTurmaService).toBe(true);
  });

});
