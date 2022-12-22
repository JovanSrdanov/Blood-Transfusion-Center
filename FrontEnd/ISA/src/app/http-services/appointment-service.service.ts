import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PremadeAppointmentDTO } from '../features/premade-appointments/premade-appointments/premade-appointments.component';
import { PageDto } from '../model/PageDto';

@Injectable({
  providedIn: 'root'
})
export class AppointmentServiceService {

  constructor(private readonly http: HttpClient) { }
  path: string = environment.backendPath + '/appointment';

  premadeAppointments(pageSize: number, page: number, sortType: any,
    field: string, centerId: string): Observable<PageDto<PremadeAppointmentDTO[]>> {
    return this.http.get<PageDto<PremadeAppointmentDTO[]>>(this.path + '/premadeAppointments/' + centerId + '?pageSize=' + pageSize + "&page=" + page + "&sort=" + sortType + "&field=" + field);
  };

  schedule(appointmentId: string): Observable<any> {
    return this.http.post<any>(this.path + '/schedulePredefine/' + appointmentId, {});
  }



}
