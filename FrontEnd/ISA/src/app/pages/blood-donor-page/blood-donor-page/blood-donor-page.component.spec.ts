import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodDonorPageComponent } from './blood-donor-page.component';

describe('BloodDonorPageComponent', () => {
  let component: BloodDonorPageComponent;
  let fixture: ComponentFixture<BloodDonorPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodDonorPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodDonorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
