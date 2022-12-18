import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class IncognitoGuard implements CanActivate {

  constructor(private readonly router: Router, private readonly auth: AuthService, private readonly jwtHelper: JwtHelperService) { }
  canActivate(): boolean {
    if (this.auth.isAuthenticated()) {
      this.redirectToMainPage();
      return false;
    }
    return true;
  }

  private redirectToMainPage = () => {
    var roleLandingPages = new Map<string, string>([
      ['ROLE_BLOOD_DONOR', 'blood-donor'],
      ['ROLE_STAFF_NO_PASSWORD', 'staff-change-password'],
      ['ROLE_STAFF', 'staff'],
      ['ROLE_SYSTEM_ADMIN', 'system-admin'],

    ]);

    const token = localStorage.getItem('jwt');
    const tokenPayload = this.jwtHelper.decodeToken(token!);
    const role = tokenPayload['role'];

    this.router.navigate([roleLandingPages.get(role)]);
  }

}
