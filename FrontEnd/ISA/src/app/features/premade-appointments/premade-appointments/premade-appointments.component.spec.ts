import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PremadeAppointmentsComponent } from './premade-appointments.component';

describe('PremadeAppointmentsComponent', () => {
  let component: PremadeAppointmentsComponent;
  let fixture: ComponentFixture<PremadeAppointmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PremadeAppointmentsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PremadeAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
