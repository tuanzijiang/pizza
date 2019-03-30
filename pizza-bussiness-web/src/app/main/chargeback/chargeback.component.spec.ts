import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChargebackComponent } from './chargeback.component';

describe('ChargebackComponent', () => {
  let component: ChargebackComponent;
  let fixture: ComponentFixture<ChargebackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChargebackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChargebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
