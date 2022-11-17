import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodDonorInfoFormComponent } from './blood-donor-info-form.component';

describe('BloodDonorInfoFormComponent', () => {
  let component: BloodDonorInfoFormComponent;
  let fixture: ComponentFixture<BloodDonorInfoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodDonorInfoFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodDonorInfoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
