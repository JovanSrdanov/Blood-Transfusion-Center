import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import  { RegisterAccountDto} from 'src/app/model/account/registerAccountDto'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SystemAdminService {

  pathBase: string = environment.backendPath; //Currently there is no special controller for system-admins
  constructor(private readonly http: HttpClient) { }

  registerSystemAdmin = (email: string, password: string) : Observable<void> =>
  {
    const path = this.pathBase + "/account/system-admin"
    const dto : RegisterAccountDto = {
      email : email,
      password : password
    }

    return this.http.post<void>(path, dto);
  }

}
