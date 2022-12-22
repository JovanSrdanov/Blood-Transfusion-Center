import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePremadeAppointmentComponent } from './create-premade-appointment.component';

describe('CreatePremadeAppointmentComponent', () => {
  let component: CreatePremadeAppointmentComponent;
  let fixture: ComponentFixture<CreatePremadeAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatePremadeAppointmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatePremadeAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
