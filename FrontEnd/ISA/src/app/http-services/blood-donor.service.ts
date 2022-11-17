import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterNonRegisteredUserDTO } from '../features/register-blood-donor/Model/RegisterNonRegisteredUserDTO';
import { environment } from 'src/environments/environment';
import { BloodDonorInfo } from '../model/blood-donor/blood-donor-info';
import { Observable } from 'rxjs';
import { BloodDonorSearchNameSurname } from '../model/blood-donor/blood-donor-search-name-surname';

@Injectable({
  providedIn: 'root'
})
export class BloodDonorService {

  path: string = environment.backendPath + "/blood-donor";
  currentId: string = "0034aea2-3260-4429-a611-388c607eb48d";

  constructor(private readonly http: HttpClient) { }

    registerUser= (dto: RegisterNonRegisteredUserDTO) =>
    {
      return this.http.post(this.path+"/register", dto);
    }

    fetchLoggedinDonor(): Observable<BloodDonorInfo> {
      return this.http.get<BloodDonorInfo>(this.path + "/get-by-id/" + this.currentId);
    }

    updateLoggedinDonor(currentDonor: BloodDonorInfo): Observable<BloodDonorInfo> {
      return this.http.patch<BloodDonorInfo>(this.path + "/update", currentDonor);
    }

    searchByNameAndUsername = (dto:BloodDonorSearchNameSurname) =>
    {
      return this.http.post<BloodDonorInfo[]>(this.path + '/search-name-surname', dto);
    }
}
