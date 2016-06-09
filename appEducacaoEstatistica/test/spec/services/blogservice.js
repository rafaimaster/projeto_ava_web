'use strict';

describe('Service: blogService', function () {

  // load the service's module
  beforeEach(module('appEducacaoEstatisticaApp'));

  // instantiate service
  var blogService;
  beforeEach(inject(function (_blogService_) {
    blogService = _blogService_;
  }));

  it('should do something', function () {
    expect(!!blogService).toBe(true);
  });

});
