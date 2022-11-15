import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BloodCenterBasicInfo } from '../model/blood-center/blood-center-basic-info';
import { BloodCenterRegistration } from '../model/blood-center/blood-center-registration';

@Injectable({
  providedIn: 'root'
})
export class BloodCenterService {

  path: string = environment.backendPath + "/blood-center";

  constructor(private readonly http:HttpClient) { }

    registerBloodCenter = (bloodCenter: BloodCenterRegistration) =>
    {
      return this.http.post(this.path, bloodCenter);
    }

    getAllBloodCenters = () =>{
      return this.http.get<BloodCenterBasicInfo[]>(this.path + '/all-basic-info');
    }
}
