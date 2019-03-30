import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperationRecordComponent } from './operation-record.component';

describe('OperationRecordComponent', () => {
  let component: OperationRecordComponent;
  let fixture: ComponentFixture<OperationRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperationRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperationRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
