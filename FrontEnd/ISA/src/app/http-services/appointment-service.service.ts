import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PremadeAppointmentDTO } from '../features/premade-appointments/premade-appointments/premade-appointments.component';
import { PageDto } from '../model/PageDto';
import {AppointmentQrInformation} from '../model/appointment/appointment-qr-information'
import { PremadeFreeSlots } from '../model/appointment/premade-free-slots';
import { FreeSlots } from '../model/appointment/free-slots';
import { PremadeAppointment } from 'src/app/model/appointment/premade-appointment';

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

  scanQrCode = (qrCode : File) : Observable<AppointmentQrInformation> => {
    const uploadData : FormData = new FormData();
    uploadData.append('qrCode', (qrCode as Blob), qrCode.name);

    return this.http.post<AppointmentQrInformation>(this.path + '/scan-qr' , uploadData);
  }

  availableSlots(dto: PremadeFreeSlots): Observable<FreeSlots[]>{
    console.log(dto);
    return this.http.post<FreeSlots[]>(this.path + '/available-admin', dto);
  }

  premadeAppointment(dto: PremadeAppointment){
    return this.http.post(this.path + '/predefine', dto);
  }


}
