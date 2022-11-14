import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BloodAdminRegistration } from '../model/blood-admin/blood-admin-registration';

@Injectable({
  providedIn: 'root'
})
export class BloodAdminService {

  path: string = environment.backendPath + "/blood-admin";
  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  registerBloodAdmin = (bloodAdmin: BloodAdminRegistration) =>
  {
    return this.http.post(this.path, bloodAdmin);
  }
}
