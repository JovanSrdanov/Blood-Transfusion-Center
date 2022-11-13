import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodAdminProfileComponent } from './blood-admin-profile.component';

describe('BloodAdminProfileComponent', () => {
  let component: BloodAdminProfileComponent;
  let fixture: ComponentFixture<BloodAdminProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodAdminProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodAdminProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
