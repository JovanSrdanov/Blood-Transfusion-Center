import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppointmentReportService {
  path: string = environment.backendPath + '/appointment-report';

  constructor(private readonly http: HttpClient) {}
}
