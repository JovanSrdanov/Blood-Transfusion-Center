import { HttpClient } from '@angular/common/http';
import { staffMemberModel } from './../../model/staff-members/staff-member-model';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  constructor(private http: HttpClient) {}

  getLoggedInStaffInfo() {
    // return {
    //   name: 'Stefan',
    //   surname: 'Apostolovic',
    //   email: 'stefan@email.com',
    //   phoneNumber: '123456789',
    // };
    return this.http.get(environment.backendPath + '/blood-admin/logged-in');
  }

  getStaffCenterInfo() {
    return {
      name: 'Centar',
      address: {
        stree: 'ulica',
        number: '1/2',
        city: 'Nis',
        country: 'Serbia',
        latitude: 21.12312,
        longitude: -15.32131,
      },
      description: 'opis',
      score: 5.0,
      appointments: [{ time: '11:00', duration: 30, available: true }],
      administrators: {},
    };
  }

  updateStaffInfo(staffId: any, staffInfo: any) {
    console.log(staffInfo);
    return this.http.put(
      environment.backendPath + '/blood-admin/updateBloodAdmin/' + staffId,
      staffInfo
    );
  }

  updateStaffCenterInfo(info: any) {
    console.log(info);
  }
}
