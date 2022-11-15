import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AssignBloodCenter } from '../model/blood-admin/assign-blood-center';
import { BloodAdminBasicInfo } from '../model/blood-admin/blood-admin-basic-info';
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

  checkEmailAvailability = (email:string) =>{
    return this.http.get(this.pathBase + '/email-available/' + email)
  }

  getUnemployedBloodAdmins = () => {
    return this.http.get<BloodAdminBasicInfo[]>(this.pathBase + '/unemployed' );
  }

  assignBloodCenter(dto: AssignBloodCenter)
  {
    return this.http.patch(this.pathBase, dto);
  }
}
