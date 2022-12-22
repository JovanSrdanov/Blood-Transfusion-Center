import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateAppointmentReport } from 'src/app/model/AppointmentReport/create-appointment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppointmentReportService {
  path: string = environment.backendPath + '/appointment-report';

  constructor(private readonly http: HttpClient) {}

  createAppointment(report: CreateAppointmentReport): Observable<any> {
    return this.http.post<any>(this.path + '/create-report', report);
  }
}
