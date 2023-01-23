import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { AccountService } from 'src/app/http-services/account.service';
import { UpdatePasswordDto } from 'src/app/model/account/updatePasswordDto';
import { LoginService } from 'src/app/auth/services/login.service';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  selector: 'app-system-admin-password-update',
  templateUrl: './system-admin-password-update.component.html',
  styleUrls: ['./system-admin-password-update.component.css']
})
export class SystemAdminPasswordUpdateComponent implements OnInit {

  changePasswordForm : FormGroup = new FormGroup({
    newPassword: new FormControl<string>(''),
  });

  get newPassword() : string{
    return this.changePasswordForm.controls['newPassword'].value;
  }

  constructor(private readonly accountService: AccountService,
              private readonly loginService : LoginService,
              private readonly authService : AuthService,
              private readonly router: Router) { }

  ngOnInit(): void {
  }

  changePassword = () :void => {
      const newPasswordDto : UpdatePasswordDto = {
        newPassword : this.newPassword
     }

      this.accountService.changePassword(newPasswordDto).subscribe({
        next: (response) => {
          //Just setting new jwt doesnt work for some reason so I logout
          // localStorage.setItem('jwt', response.jwt);
          // this.router.navigate(['blood-center-view']);
          alert("You will need to login again with new password");
          this.loginService.logout();
        },

        error: err => {
          alert("Invalid password format(Min 8 characters)");
        }
      })

  }
}
