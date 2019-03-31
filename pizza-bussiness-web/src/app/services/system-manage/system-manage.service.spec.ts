import { TestBed } from '@angular/core/testing';

import { SystemManageService } from './system-manage.service';

describe('SystemManageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SystemManageService = TestBed.get(SystemManageService);
    expect(service).toBeTruthy();
  });
});
