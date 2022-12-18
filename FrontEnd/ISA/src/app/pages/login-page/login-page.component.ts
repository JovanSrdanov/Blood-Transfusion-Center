import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/auth/services/account.service';
import { LoginService } from 'src/app/auth/services/login.service';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  loginForm = new FormGroup({
    email: new FormControl<string>(''),
    password: new FormControl<string>(''),
  });

  get email() {
    return this.loginForm.controls.email.value;
  }
  get password() {
    return this.loginForm.controls.password.value;
  }


  constructor(private readonly loginService: LoginService,
    private readonly router: Router,
    private readonly accountService: AccountService) { }

  ngOnInit(): void {
    const params = new URLSearchParams(window.location.search);
    var token = params.get('token');
    var id = params.get('id');


  }

  login = () => {
    let JwtAuthenticationRequest = {
      email: this.email!,
      password: this.password!,
    };

    this.loginService.login(JwtAuthenticationRequest);

  };

}
