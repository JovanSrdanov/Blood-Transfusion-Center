import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodDonorAppointmentListComponent } from './blood-donor-appointment-list.component';

describe('BloodDonorAppointmentListComponent', () => {
  let component: BloodDonorAppointmentListComponent;
  let fixture: ComponentFixture<BloodDonorAppointmentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodDonorAppointmentListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodDonorAppointmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
