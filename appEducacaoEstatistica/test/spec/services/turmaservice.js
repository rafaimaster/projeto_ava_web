'use strict';

describe('Service: turmaService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var turmaService;
  beforeEach(inject(function (_turmaService_) {
    turmaService = _turmaService_;
  }));

  it('should do something', function () {
    expect(!!turmaService).toBe(true);
  });

});
