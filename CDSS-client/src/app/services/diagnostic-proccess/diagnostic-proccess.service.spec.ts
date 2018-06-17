import { TestBed, inject } from '@angular/core/testing';

import { DiagnosticProccessService } from './diagnostic-proccess.service';

describe('DiagnosticProccessService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DiagnosticProccessService]
    });
  });

  it('should be created', inject([DiagnosticProccessService], (service: DiagnosticProccessService) => {
    expect(service).toBeTruthy();
  }));
});
