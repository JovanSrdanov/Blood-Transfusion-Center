import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { AccountService } from 'src/app/http-services/account.service';
import { UpdatePasswordDto } from 'src/app/model/account/updatePasswordDto';

@Component({
  selector: 'app-system-admin-password-update',
  templateUrl: './system-admin-password-update.component.html',
  styleUrls: ['./system-admin-password-update.component.css']
})
export class SystemAdminPasswordUpdateComponent implements OnInit {

  changePasswordForm : FormGroup = new FormGroup({
    newPassword: new FormControl<string>(''),
  });

  get newPassword(){
    return this.changePasswordForm.controls['newPassword'].value;
  }

  constructor(private readonly accountService: AccountService, private readonly router: Router) { }

  ngOnInit(): void {
  }

  changePassword = () :void => {
      const newPasswordDto : UpdatePasswordDto = {
        newPassword : this.newPassword
     }

      this.accountService.changePassword(newPasswordDto).subscribe({
        next: (response) => {
          localStorage.setItem('jwt', response.jwt);
          this.router.navigate(['blood-center-view']);
        },

        error: err => {
          alert("Invalid password format(Min 8 characters)");
        }
      })

  }
}
