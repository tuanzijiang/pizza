import { TestBed } from '@angular/core/testing';

import { ChargebackService } from './chargeback.service';

describe('ChargebackService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChargebackService = TestBed.get(ChargebackService);
    expect(service).toBeTruthy();
  });
});
