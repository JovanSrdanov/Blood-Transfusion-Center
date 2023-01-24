import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StaffFreeSlotsInfo } from 'src/app/model/staff/staff-free-slots-info';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  constructor(private http: HttpClient) {}

  getLoggedInStaffInfo() {
    return this.http.get(environment.backendPath + '/staff/logged-in');
  }

  updateStaffInfo(staffId: any, staffInfo: any) {
    console.log(staffInfo);
    return this.http.put(
      environment.backendPath + '/staff/updateBloodAdmin/' + staffId,
      staffInfo
    );
  }

  updateStaffCenterInfo(centerId: any, centerInfo: any) {
    console.log(centerInfo);
    return this.http.put(
      environment.backendPath + '/blood-center/' + centerId,
      centerInfo
    );
  }

  changePassword(staffId: any, passwordInfo: any) {
    console.log(staffId);
    console.log(passwordInfo);
    return this.http.patch(
      environment.backendPath + '/staff/change-password/' + staffId,
      passwordInfo
    );
  }

  getStaffInfoForFreeSlots(): Observable<StaffFreeSlotsInfo[]> {
    return this.http.get<StaffFreeSlotsInfo[]>(environment.backendPath + '/staff/by-blood-center');
  }
}
