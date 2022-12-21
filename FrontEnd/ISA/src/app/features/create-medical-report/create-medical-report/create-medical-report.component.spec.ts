import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMedicalReportComponent } from './create-medical-report.component';

describe('CreateMedicalReportComponent', () => {
  let component: CreateMedicalReportComponent;
  let fixture: ComponentFixture<CreateMedicalReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMedicalReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMedicalReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
