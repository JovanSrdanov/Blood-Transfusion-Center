import { TestBed } from '@angular/core/testing';

import { AppointmentReportService } from './appointment-report.service';

describe('AppointmentReportService', () => {
  let service: AppointmentReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppointmentReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
