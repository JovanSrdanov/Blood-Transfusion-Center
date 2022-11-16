import { ProfileService } from './../../../http-services/staff/profile.service';
import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
  ValidationErrors,
} from '@angular/forms';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  isPreventChangePassword: boolean = true;

  changePasswordForm: FormGroup;

  newPassword: String = '';
  confirmPassword: String = '';

  newPasswordCopy: String = '';
  confirmPasswordCopy: String = '';

  @Input() loggedInStaffId: any;

  constructor(private profileService: ProfileService, private fb: FormBuilder) {
    this.changePasswordForm = this.fb.group({
      newPassword: '',
      confirmPassword: '',
    });
  }

  ngOnInit(): void {
    this.newPasswordCopy = structuredClone(this.newPassword);
    this.confirmPassword = structuredClone(this.confirmPassword);

    this.createFormChangePassword();
  }

  createFormChangePassword() {
    console.log(this.newPassword);
    console.log(this.confirmPassword);
    this.changePasswordForm = this.fb.group(
      {
        newPassword: [
          {
            value: this.newPassword,
          },
          [Validators.required, Validators.pattern('^[a-zA-Z0-9]{8,}$')],
        ],
        confirmPassword: [
          { value: this.confirmPassword },
          [Validators.required, Validators.pattern('^[a-zA-Z0-9]{8,}$')],
        ],
      },
      { validator: this.matchPassword }
    );
    this.changePasswordForm.valueChanges.subscribe((currValue) => {
      this.newPassword = currValue.newPassword;
      this.confirmPassword = currValue.confirmPassword;
    });
  }

  matchPassword(control: AbstractControl): ValidationErrors | null {
    const password = control.get('newPassword')?.value;
    const confirm = control.get('confirmPassword')?.value;

    if (password != confirm) {
      return { noMatch: true };
    }
    return null;
  }

  enableChangePassword(event: Event) {
    event.preventDefault;
    this.changePasswordForm.get('newPassword')?.setValue('');
    this.changePasswordForm.get('confirmPassword')?.setValue('');
    this.isPreventChangePassword = !this.isPreventChangePassword;
  }

  confirmChangePassword(event: Event) {
    event.preventDefault;

    console.log(this.loggedInStaffId);
    this.profileService
      .changePassword(this.loggedInStaffId, {
        newPassword: this.newPassword,
        confirmPassword: this.confirmPassword,
      })
      .subscribe((res) => {
        console.log(res);
      });
    this.isPreventChangePassword = true;
  }

  cancelChangePassword(event: Event) {
    event.preventDefault;

    this.newPassword = structuredClone(this.newPasswordCopy);
    this.confirmPassword = structuredClone(this.confirmPasswordCopy);
    this.changePasswordForm.patchValue({
      newPassword: this.newPassword,
      confirmPassword: this.confirmPassword,
    });

    this.isPreventChangePassword = true;
  }
}
