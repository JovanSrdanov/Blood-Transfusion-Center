import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AssignBloodCenter } from '../model/staff/assign-blood-center';
import { StaffBasicInfo } from '../model/staff/staff-basic-info';
import { StaffRegistration } from '../model/staff/staff-registration';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  pathBase: string = environment.backendPath + "/staff";
  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  registerStaff = (staff: StaffRegistration) =>
  {
    return this.http.post(this.pathBase, staff);
  }

  checkEmailAvailability = (email:string) =>{
    return this.http.get(this.pathBase + '/email-available/' + email)
  }

  getUnemployedStaff = () => {
    return this.http.get<StaffBasicInfo[]>(this.pathBase + '/unemployed' );
  }

  assignBloodCenter(dto: AssignBloodCenter)
  {
    return this.http.patch(this.pathBase, dto);
  }
}
