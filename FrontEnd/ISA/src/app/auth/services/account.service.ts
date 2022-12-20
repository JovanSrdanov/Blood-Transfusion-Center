import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Jwt } from '../Model/Jwt.model';
import { environment } from './../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) { }

  //TODO stefan radio za psw, jovan prepravka za ISU
  activateAccount(activationCode: string, accountId: string) {
    return this.http.post<Jwt>(
      environment.backendPath + '/auth/activate-account',
      { activationCode: activationCode, accountId: accountId },
    );
  }
}
