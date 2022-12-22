import { AppointmentCancelation } from './../model/appointment/appointment-cancelation';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BloodDonorAppointmentsDTO } from '../features/my-appointments/my-appointments/my-appointments.component';
import { Observable } from 'rxjs';
import { PageDto } from '../model/PageDto';
import { appointmentBloodDonorInfo } from '../model/appointment/appointment-blood-donor-info';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  // cancelAppointment(id: string): Observable<any> {
  //   return this.http.get<any>(this.path + '/cancel/' + id);
  // }

  constructor(private readonly http: HttpClient) {}
  path: string = environment.backendPath + '/appointment_scheduling_history';

  // bloodDonorAppointments(
  //   pageSize: number,
  //   page: number,
  //   sortType: any,
  //   field: string
  // ): Observable<PageDto<BloodDonorAppointmentsDTO[]>> {
  //   return this.http.get<PageDto<BloodDonorAppointmentsDTO[]>>(
  //     this.path +
  //       '/blood-donor-appointments?pageSize=' +
  //       pageSize +
  //       '&page=' +
  //       page +
  //       '&sort=' +
  //       sortType +
  //       '&field=' +
  //       field
  //   );
  // }

  bloodDonorAppointmentsForCenter(donorId: string): Observable<any> {
    console.log();
    let path: string =
      environment.backendPath +
      '/appointment_scheduling_history/blood-donor-appointments-for-centre/';
    console.log(path);
    return this.http.get<any>(path + donorId);
  }

  staffCancelAppointment(input: AppointmentCancelation): Observable<any> {
    return this.http.put(this.path + '/cancel-appointment', input);
  }

  cancelAppointment(appointmentId: string): Observable<any> {
    return this.http.get<any>(
      this.path + '/cancel-appointment/' + appointmentId
    );
  }

  bloodDonorAppointments(
    pageSize: number,
    page: number,
    sortType: any,
    field: string
  ): Observable<PageDto<BloodDonorAppointmentsDTO[]>> {
    return this.http.get<PageDto<BloodDonorAppointmentsDTO[]>>(
      this.path +
        '/blood-donor-appointments?pageSize=' +
        pageSize +
        '&page=' +
        page +
        '&sort=' +
        sortType +
        '&field=' +
        field
    );
  }
}
