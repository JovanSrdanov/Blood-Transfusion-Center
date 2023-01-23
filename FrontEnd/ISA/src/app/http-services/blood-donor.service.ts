import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterNonRegisteredUserDTO } from '../pages/registration-page/Model/RegisterNonRegisteredUserDTO';
import { BloodDonorInfo } from '../model/blood-donor/blood-donor-info';
import { Observable } from 'rxjs';
import { BloodDonorSearchNameSurname } from '../model/blood-donor/blood-donor-search-name-surname';
import { environment } from 'src/environments/environment';
import { PageDto } from '../model/PageDto';
import { AppointmentStatus } from '../model/appointment/appointment-status'

@Injectable({
  providedIn: 'root'
})
export class BloodDonorService {

  path: string = environment.backendPath + "/blood-donor";
  //TODO ?
  currentId: string = "0034aea2-3260-4429-a611-388c607eb48d";

  constructor(private readonly http: HttpClient) { }

  registerUser = (dto: RegisterNonRegisteredUserDTO) => {
    return this.http.post(this.path + "/register", dto);
  }

  fetchLoggedinDonor(): Observable<BloodDonorInfo> {
    return this.http.get<BloodDonorInfo>(this.path + "/my-info");
  }

  updateLoggedinDonor(currentDonor: BloodDonorInfo): Observable<BloodDonorInfo> {
    return this.http.patch<BloodDonorInfo>(this.path + "/update", currentDonor);
  }

  searchByNameAndUsername = (dto: BloodDonorSearchNameSurname) : Observable<PageDto<BloodDonorInfo>> => {
    return this.http.post<PageDto<BloodDonorInfo>>(this.path + '/get-by-name-surname', dto);
  }

  searchByNameAndUsernameForCenterAndStatus = (dto: BloodDonorSearchNameSurname, status : AppointmentStatus ) : Observable<PageDto<BloodDonorInfo>> => {
    return this.http.post<PageDto<BloodDonorInfo>>(this.path + '/get-by-name-surname-for-center-and-status/' + status, dto);
  }
}
