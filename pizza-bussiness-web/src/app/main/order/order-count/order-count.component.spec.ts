import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderCountComponent } from './order-count.component';

describe('OrderCountComponent', () => {
  let component: OrderCountComponent;
  let fixture: ComponentFixture<OrderCountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderCountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
