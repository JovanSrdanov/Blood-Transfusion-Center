import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterNonRegisteredUserDTO } from '../features/register-blood-donor/Model/RegisterNonRegisteredUserDTO';
import { environment } from 'src/environments/environment';
import { BloodDonorInfo } from '../model/blood-donor/blood-donor-info';
import { Observable } from 'rxjs';
import { LoyatyInfo } from '../model/loyalty/loyalty-info';

@Injectable({
  providedIn: 'root'
})
export class LoyaltyServiceService {
  path: string = environment.backendPath + "/loyalty";

  constructor(private readonly axios: HttpClient) { }

  fetchLoyalty(points: number): Observable<LoyatyInfo> {
    return this.axios.get<LoyatyInfo>(this.path + "/get-by-points?points=" + points);
  }
}
