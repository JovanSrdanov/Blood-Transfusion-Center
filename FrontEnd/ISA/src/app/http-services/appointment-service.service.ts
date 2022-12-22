import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PremadeAppointmentDTO } from '../features/premade-appointments/premade-appointments/premade-appointments.component';
import { PremadePost } from '../model/appointment/premadePost';
import { PremadeRequest } from '../model/appointment/premadeReqest';
import { PremadeTime } from '../model/appointment/premadeTime';
import { PageDto } from '../model/PageDto';
import { StaffPremade } from '../model/staff/staff-premade';

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

  getStaff(): Observable<StaffPremade[]> {
    return this.http.get<StaffPremade[]>(environment.backendPath + '/blood-center/get-staff/');
  }

  getPremadeList(dto: PremadeRequest): Observable<PremadeTime[]> {
    return this.http.post<PremadeTime[]>(this.path + '/available-admin', dto);
  }

  premadeAppointment(dto: PremadePost): Observable<any> {
    return this.http.post<PremadeTime>(this.path + '/predefine', dto);
  }

}
