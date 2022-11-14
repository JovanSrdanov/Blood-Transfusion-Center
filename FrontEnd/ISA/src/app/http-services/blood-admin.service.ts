import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BloodAdminRegistration } from '../model/blood-admin/blood-admin-registration';

@Injectable({
  providedIn: 'root'
})
export class BloodAdminService {

  pathBase: string = environment.backendPath + "/blood-admin";
  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  registerBloodAdmin = (bloodAdmin: BloodAdminRegistration) =>
  {
    return this.http.post(this.pathBase, bloodAdmin);
  }
  /*

    return this._HttpClient.get(`${API_URL}/api/v1/data/logs`, { params: params })
}
  */

  checkUsernameAvailability = (username:string) =>{
    return this.http.get(this.pathBase + '/username-available/' + username)
  }

  checkEmailAvailability = (email:string) =>{
    return this.http.get(this.pathBase + '/email-available/' + email)
  }
}
