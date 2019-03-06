import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FactoryManageComponent } from './factory-manage.component';

describe('FactoryManageComponent', () => {
  let component: FactoryManageComponent;
  let fixture: ComponentFixture<FactoryManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FactoryManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FactoryManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
