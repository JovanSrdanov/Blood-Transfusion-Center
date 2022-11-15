import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterBloodDonorComponent } from './register-blood-donor.component';

describe('RegisterUserComponent', () => {
  let component: RegisterBloodDonorComponent;
  let fixture: ComponentFixture<RegisterBloodDonorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterBloodDonorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterBloodDonorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
