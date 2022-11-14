import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BloodAdminService } from 'src/app/http-services/blood-admin.service';
import { BloodAdminRegistration } from 'src/app/model/blood-admin/blood-admin-registration';
//I dont know why I need to import it but it wont work if not
import { BloodAdminRegistrationModule }  from   'src/app/features/blood-admin-registration/blood-admin-registration.module'

@Component({
  selector: 'app-blood-admin-registration',
  templateUrl: './blood-admin-registration.component.html',
  styleUrls: ['./blood-admin-registration.component.css']
})
export class BloodAdminRegistrationComponent implements OnInit {

    registrationForm = new FormGroup({
      username : new FormControl<string>('', [Validators.required]),
      password : new FormControl<string>('', [Validators.required]),
      name : new FormControl<string>('', [Validators.required]),
      surname : new FormControl<string>('', [Validators.required]),
      phoneNumber : new FormControl<string>('', [Validators.required]),
      email : new FormControl<string>('', [Validators.required]),
      street : new FormControl<string>('', [Validators.required]),
      number : new FormControl<string>('', [Validators.required]),
      city : new FormControl<string>('', [Validators.required]),
      country : new FormControl<string>('', [Validators.required]),
      latitude : new FormControl<number>(0, Validators.required),
      longitude : new FormControl<number>(0, Validators.required)
    })

  usernameAvailable: boolean = true;
  emailAvailable: boolean = true;
  _disableSubmit: boolean = !this.usernameAvailable || !this.emailAvailable || this.registrationForm.invalid

  get form(){
    return this.registrationForm.controls;
  }

  get disableSubmit():boolean{
    return  !this.usernameAvailable || !this.emailAvailable || this.registrationForm.invalid;
  }

  constructor(private readonly bloodAdminService: BloodAdminService,
    private readonly router: Router) { }

  ngOnInit(): void {
  }

  register = () =>
  {
    let bloodAdmin : BloodAdminRegistration = {
      username: this.form.username.value ?? "",
      password: this.form.password.value ?? "",
      name: this.form.name.value ?? "",
      surname: this.form.surname.value ?? "",
      phoneNumber: this.form.phoneNumber.value ?? "",
      email: this.form.email.value ?? "",
      address:{
        street: this.form.street.value ?? "",
        number: this.form.number.value ?? "",
        city: this.form.city.value ?? "",
        country: this.form.country.value ?? "",
        latitude: this.form.latitude.value ?? 0,
        longitude: this.form.longitude.value ?? 0
      }
    }
    this.bloodAdminService.registerBloodAdmin(bloodAdmin).subscribe(_ => {
      this.router.navigate(['']);
    })

  }

  checkUsernameAvailability = () =>{
      this.bloodAdminService.checkUsernameAvailability(this.form.username.value ?? '').subscribe(response =>{
        if(response){
          this.usernameAvailable = true;
        }
        else
        {
          this.usernameAvailable = false;
        }
      })
  }

  checkEmailAvailability = () =>{
      this.bloodAdminService.checkEmailAvailability(this.form.email.value ?? '').subscribe(response =>{
        if(response){
          this.emailAvailable = true;
        }
        else
        {
          this.emailAvailable = false;
        }
      })
  }

}
