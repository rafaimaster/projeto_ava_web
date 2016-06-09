'use strict';

describe('Service: alternativaService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var alternativaService;
  beforeEach(inject(function (_alternativaService_) {
    alternativaService = _alternativaService_;
  }));

  it('should do something', function () {
    expect(!!alternativaService).toBe(true);
  });

});
