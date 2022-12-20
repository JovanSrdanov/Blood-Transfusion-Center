import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Jwt } from "../Model/Jwt.model";
import { Router } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import { LoginDto } from "../Model/loginDto";
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private readonly router: Router, private readonly jwtHelper: JwtHelperService) { }

  private redirectToMainPage = () => {
    var roleLandingPages = new Map<string, string>([
      ['ROLE_BLOOD_DONOR', 'blood-donor'],
      ['ROLE_STAFF', 'staff'],
      ['ROLE_SYSTEM_ADMIN', 'system-admin'],

    ]);

    const token = localStorage.getItem('jwt');
    const tokenPayload = this.jwtHelper.decodeToken(token!);
    const role = tokenPayload['role'];

    this.router.navigate([roleLandingPages.get(role)]);
  }

  public login = (loginCredentials: LoginDto): void => {


    this.http.post<Jwt>(environment.backendPath + '/auth/login', loginCredentials)
      .subscribe({
        next: (response) => {
          localStorage.setItem('jwt', response.jwt);
          this.redirectToMainPage();
        },
        //TODO: handle errors
        error: err => {
          console.log(err)
          alert(err.error);
        }
      }
      );
  }

  public logout = () => {
    localStorage.removeItem('jwt');
    this.router.navigate(['login']);
  }

}
