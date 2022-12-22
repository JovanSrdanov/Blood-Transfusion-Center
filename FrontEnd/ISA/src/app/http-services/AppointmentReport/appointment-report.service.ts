import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateAppointmentReport } from 'src/app/model/AppointmentReport/create-appointment';
import { AppointmentReportDTO } from 'src/app/features/visiting-history/visiting-history/visiting-history.component';
import { PageDto } from 'src/app/model/PageDto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppointmentReportService {
  path: string = environment.backendPath + '/appointment-report';
  constructor(private readonly http: HttpClient) {}

  reportHistory(
    pageSize: number,
    pageIndex: number,
    direction: string,
    active: string
  ): Observable<PageDto<AppointmentReportDTO[]>> {
    return this.http.get<PageDto<AppointmentReportDTO[]>>(
      this.path +
        '/my-reports' +
        '?pageSize=' +
        pageSize +
        '&page=' +
        pageIndex +
        '&sort=' +
        direction +
        '&field=' +
        active
    );
  }

  createAppointment(report: CreateAppointmentReport): Observable<any> {
    return this.http.post<any>(this.path + '/create-report', report);
  }
}
