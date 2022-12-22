import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { BloodCenterBasicInfo } from '../model/blood-center/blood-center-basic-info';
import { BloodCenterRegistration } from '../model/blood-center/blood-center-registration';
import { PageDto } from '../model/PageDto';
import { BloodCenterRoundedWorkingHours } from '../model/blood-center/blood-center-rounded-working-hours';
import { BloodCenterCalendarAppointment } from '../model/blood-center/blood-center-calendar-appointment';

@Injectable({
  providedIn: 'root',
})
export class BloodCenterService {
  path: string = environment.backendPath + '/blood-center';

  constructor(private readonly http: HttpClient) { }

  registerBloodCenter = (bloodCenter: BloodCenterRegistration) => {
    return this.http.post(this.path, bloodCenter);
  };

  getAllBloodCenters = () => {
    return this.http.get<BloodCenterBasicInfo[]>(this.path + '/all-basic-info');
  };

  getWorkingHoursRounded = () :Observable<BloodCenterRoundedWorkingHours> => {
    return this.http.get<BloodCenterRoundedWorkingHours>(this.path + '/working-hours-rounded');
  };

  getAppointments = () :Observable<BloodCenterCalendarAppointment[]> => {
    return this.http.get<BloodCenterCalendarAppointment[]>(this.path + '/incoming-appointments');
  };

  getPagableBloodCenters(
    page: number,
    search: string,
    sortType: any,
    field: string
  ): Observable<PageDto<BloodCenterBasicInfo[]>> {
    //alert("sortType: " + sortType + "  sortField: " + field + " search:" + search)
    return this.http.get<PageDto<BloodCenterBasicInfo[]>>(
      this.path +
      '/sort?page=' +
      page +
      '&field=' +
      field +
      '&sort=' +
      sortType +
      '&s=' +
      search
    );
  }
}
