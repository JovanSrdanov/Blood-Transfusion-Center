import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodCenterTableComponent } from './blood-center-table.component';

describe('BloodCenterTableComponent', () => {
  let component: BloodCenterTableComponent;
  let fixture: ComponentFixture<BloodCenterTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodCenterTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodCenterTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
