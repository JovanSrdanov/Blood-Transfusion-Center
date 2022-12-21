import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { UpdatePasswordDto } from '../model/Account/updatePasswordDto';
import { Observable } from 'rxjs';
import { Jwt } from '../auth/Model/Jwt.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  pathBase: string = environment.backendPath + "/account";
  constructor(private readonly http: HttpClient) { }

  changePassword = (newPassword: UpdatePasswordDto) : Observable<Jwt> =>
  {
    const path = this.pathBase + "/password"
    return this.http.patch<Jwt>(path, newPassword);
  }

}
