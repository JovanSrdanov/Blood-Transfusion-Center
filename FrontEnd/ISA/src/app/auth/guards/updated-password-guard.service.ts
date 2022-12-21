import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UpdatedPasswordGuardService implements CanActivate {

  constructor(private readonly jwtHelper : JwtHelperService, private readonly router: Router) { }
    canActivate(): boolean {

    const token = localStorage.getItem('jwt');
    const tokenPayload = this.jwtHelper.decodeToken(token!);

    const  lastPasswordUpdateDateString : string = tokenPayload['lastPasswordUpdateDate'];
    const lastPasswordUpdateDate : Date = new Date(lastPasswordUpdateDateString);


    const defaultNoUpdateDate = new Date("0001-01-01T00:00");

    if(lastPasswordUpdateDate.getTime() === defaultNoUpdateDate.getTime())
    {
      this.router.navigate(['system-admin/first-password-update']);
      return false
    }
    return true;
  }
}
