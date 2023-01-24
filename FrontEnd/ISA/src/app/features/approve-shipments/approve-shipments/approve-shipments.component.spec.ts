import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveShipmentsComponent } from './approve-shipments.component';

describe('ApproveShipmentsComponent', () => {
  let component: ApproveShipmentsComponent;
  let fixture: ComponentFixture<ApproveShipmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApproveShipmentsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApproveShipmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
