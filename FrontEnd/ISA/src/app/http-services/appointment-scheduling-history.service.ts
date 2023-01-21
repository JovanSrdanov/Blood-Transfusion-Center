import { AppointmentCancelation } from './../model/appointment/appointment-cancelation';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BloodDonorAppointmentsDTO } from '../features/my-appointments/my-appointments/my-appointments.component';
import { Observable } from 'rxjs';
import { PageDto } from '../model/PageDto';

export interface QrCodeASHDTO {
  ashid: String,
  qrcodeMessage: string
  status: string,
  issuingDate: Date
}
import { appointmentBloodDonorInfo } from '../model/appointment/appointment-blood-donor-info';
import { BloodDonorSearchNameSurname } from '../model/blood-donor/blood-donor-search-name-surname';

@Injectable({
  providedIn: 'root',
})
export class AppointmentSchedulingHistoryService {
  constructor(private readonly http: HttpClient) { }
  path: string = environment.backendPath + '/appointment_scheduling_history';

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

  QRbloodDonorAppointments(pageSize: number,
    page: number,
    sortType: any,
    filter: string): Observable<PageDto<QrCodeASHDTO[]>> {
    return this.http.get<PageDto<QrCodeASHDTO[]>>(this.path + '/blood-donor-appointments-QR?page='
      + page + "&pageSize=" + pageSize + "&filter=" + filter + "&sort=" + sortType);
  }

  getDonorFullname(ashId: string): Observable<BloodDonorSearchNameSurname> {
    return this.http.get<BloodDonorSearchNameSurname>(
      this.path + '/fullname/' + ashId
    );
  }
}
