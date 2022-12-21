import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(private readonly auth: AuthService, private readonly router: Router, private readonly jwtHelper: JwtHelperService) { }
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['expectedRole'];
    const token = localStorage.getItem('jwt');
    if (!token) {
      this.router.navigate(['login']);
      return false;
    }

    const tokenPayload = this.jwtHelper.decodeToken(token!);
    const role = tokenPayload['role'];

    if (
      !this.auth.isAuthenticated() ||
      role !== expectedRole
    ) {
      this.router.navigate(['login']);
      return false;
    }
    return true;

  }

}
