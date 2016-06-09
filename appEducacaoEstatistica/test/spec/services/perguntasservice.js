'use strict';

describe('Service: perguntaSservice', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var perguntaSservice;
  beforeEach(inject(function (_perguntaSservice_) {
    perguntaSservice = _perguntaSservice_;
  }));

  it('should do something', function () {
    expect(!!perguntaSservice).toBe(true);
  });

});
