import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly jwtHelper: JwtHelperService) { }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('jwt');
    if (token) {
      return !this.jwtHelper.isTokenExpired(token!);
    }
    return false;
  }
}
