import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class InverseUpdatedPasswordGuardService {

  constructor(private readonly jwtHelper : JwtHelperService, private readonly router: Router) { }
    canActivate(): boolean {

    const token = localStorage.getItem('jwt');

    const tokenPayload = this.jwtHelper.decodeToken(token!);
    const  lastPasswordUpdateDateString : string = tokenPayload['lastPasswordUpdateDate'];

    const lastPasswordUpdateDate : Date = new Date(lastPasswordUpdateDateString);
    let defaultNoUpdateDate = new Date("0001-01-01T00:00");
    if(lastPasswordUpdateDate.getTime() !== defaultNoUpdateDate.getTime())
    {
      this.router.navigate(['blood-center-view'])
      return false
    }
    return true;
  }
}
