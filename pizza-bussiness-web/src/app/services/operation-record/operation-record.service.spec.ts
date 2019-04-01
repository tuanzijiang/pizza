import { TestBed } from '@angular/core/testing';

import { OperationRecordService } from './operation-record.service';

describe('OperationRecordService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OperationRecordService = TestBed.get(OperationRecordService);
    expect(service).toBeTruthy();
  });
});
