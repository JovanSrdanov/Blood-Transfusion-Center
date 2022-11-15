import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterUserService } from 'src/app/http-services/register-user.service';
import { RegisterNonRegisteredUserDTO } from '../Model/RegisterNonRegisteredUserDTO';
interface Gender {
  value: number;
  viewValue: string;
}

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css'],
})
export class RegisterUserComponent implements OnInit {
  hide = true;
  genders: Gender[] = [
    { value: 0, viewValue: 'Male' },
    { value: 1, viewValue: 'Female' },
  ];

  registrationForm = new FormGroup({

    password: new FormControl<string>('', [Validators.required]),
    passwordReEnter: new FormControl<string>('', [Validators.required]),
    number: new FormControl<string>('', [Validators.required]),
    city: new FormControl<string>('', [Validators.required]),
    street: new FormControl<string>('', [Validators.required]),
    country: new FormControl<string>('', [Validators.required]),
    name: new FormControl<string>('', [Validators.required]),
    surname: new FormControl<string>('', [Validators.required]),
    email: new FormControl<string>('', [Validators.required]),
    phoneNumber: new FormControl<string>('', [Validators.required]),
    institution: new FormControl<string>('', [Validators.required]),
    occupation: new FormControl<string>('', [Validators.required]),
    jmbg: new FormControl<string>('', [Validators.required])
  })

  selectedGender = this.genders[0].value;

  errorMessage: string = "Fill the form data correctly";

  constructor(private readonly registerUserService: RegisterUserService, private readonly router: Router) { }

  ngOnInit(): void { }

  get form() {
    return this.registrationForm.controls;
  }

  registerUser(): void {

    this.errorMessage = "Fill the form data correctly";

    if (this.form.password.value !== this.form.passwordReEnter.value) {
      this.errorMessage = "Correctly input the password twice.";
      return;
    }


    let registerNonRegisteredUserDTO: RegisterNonRegisteredUserDTO = {
      bloodUserDTO: {

        password: this.form.password.value ?? "",
      },
      addressRegUserDTO: {
        number: this.form.number.value ?? "",
        city: this.form.city.value ?? "",
        street: this.form.street.value ?? "",
        country: this.form.country.value ?? "",
      },
      nonRegisteredUserInfoDTO: {
        occupation:this.form.name.value ?? "",
        name: this.form.name.value ?? "",
        surname: this.form.surname.value ?? "",
        email: this.form.email.value ?? "",
        phoneNumber: this.form.phoneNumber.value ?? "",
        institution: this.form.institution.value ?? "",
        gender: this.selectedGender,
        jmbg: this.form.jmbg.value ?? "",
      }
    };

    this.registerUserService.registerUser(registerNonRegisteredUserDTO).subscribe(res => {
      console.log(res);
      this.router.navigate(['']);

    },
      err => {
        if (err.status == 409) {
          this.errorMessage = err.error;
          console.log(err.error);
          return;
        }

        if (err.status == 400) {
          this.errorMessage = "Form data invalid. Fill the form data correctly";
          console.log(err.error);
          return;
        }
        this.errorMessage = "Unepected error, try again";
        console.log(err.error);
      }

    )


  }


}
