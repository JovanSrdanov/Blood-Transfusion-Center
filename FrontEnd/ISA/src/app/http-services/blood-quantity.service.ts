import { bloodUpdate } from './../model/blood/blood-update';
import { bloodInfo } from './../model/blood/blood-info';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BloodQuantityService {
  path: string = environment.backendPath + '/blood-quantity';

  constructor(private readonly http: HttpClient) {}

  getBloodQuantityForCentre(): Observable<bloodInfo[]> {
    return this.http.get<bloodInfo[]>(this.path + '/get-by-centre');
  }

  updateBloodQuantityInCentre(bloodUpdate: bloodUpdate): Observable<any> {
    return this.http.put<any>(this.path, bloodUpdate);
  }
}
