import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterNonRegisteredUserDTO } from '../features/register-blood-donor/Model/RegisterNonRegisteredUserDTO';
import { environment } from 'src/environments/environment';
import { BloodDonorSearchNameSurname } from '../model/blood-donor/blood-donor-search-name-surname';
import { BloodDonorInfo } from '../model/blood-donor/blood-donor-info';

@Injectable({
  providedIn: 'root'
})
export class BloodDonorService {

  path: string = environment.backendPath + "/blood-donor";

  constructor(private readonly http: HttpClient) { }

    registerUser= (dto: RegisterNonRegisteredUserDTO) =>
    {
      return this.http.post(this.path+"/register", dto);
    }

    searchByNameAndUsername = (dto:BloodDonorSearchNameSurname) =>
    {
      return this.http.post<BloodDonorInfo[]>(this.path + '/search-name-surname', dto);
    }
}
