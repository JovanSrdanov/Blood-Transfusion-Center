import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) { }

  //TODO stefan radio za psw, jovan prepravka za ISU
  activateAccount(token: any, id: any) {
    return this.http.post(
      environment.backendPath + 'api/User/ActivateAccount',
      { token: token, id: id },
    );
  }
}
