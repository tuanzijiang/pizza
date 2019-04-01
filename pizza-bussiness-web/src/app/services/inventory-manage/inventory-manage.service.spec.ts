import { TestBed } from '@angular/core/testing';

import { InventoryManageService } from './inventory-manage.service';

describe('InventoryManageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InventoryManageService = TestBed.get(InventoryManageService);
    expect(service).toBeTruthy();
  });
});
