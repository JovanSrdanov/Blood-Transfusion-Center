import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  constructor() {}

  getLoggedInStaffInfo() {
    return {
      name: 'Stefan',
      surname: 'Apostolovic',
      email: 'stefan@email.com',
      phoneNumber: '123456789',
    };
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

  updateStaffInfo(info: any) {
    console.log(info);
  }

  updateStaffCenterInfo(info: any) {
    console.log(info);
  }
}
