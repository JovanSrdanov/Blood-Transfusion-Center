import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BloodDonorAppointmentsDTO } from '../features/my-appointments/my-appointments/my-appointments.component';
import { Observable } from 'rxjs';
import { PageDto } from '../model/PageDto';

@Injectable({
  providedIn: 'root'
})
export class AppointmentSchedulingHistoryService {
  constructor(private readonly http: HttpClient) { }
  path: string = environment.backendPath + '/appointment_scheduling_history';

  cancelAppointment(appointmentId: string): Observable<any> {
    return this.http.get<any>(this.path + '/cancel-appointment/' + appointmentId);
  }

  bloodDonorAppointments(pageSize: number, page: number, sortType: any,
    field: string): Observable<PageDto<BloodDonorAppointmentsDTO[]>> {
    return this.http.get<PageDto<BloodDonorAppointmentsDTO[]>>(this.path + '/blood-donor-appointments?pageSize=' + pageSize + "&page=" + page + "&sort=" + sortType + "&field=" + field);
  };
}
