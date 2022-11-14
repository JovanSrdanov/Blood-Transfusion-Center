import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BloodCenterService } from 'src/app/http-services/blood-center.service';
import { BloodCenterRegistration } from 'src/app/model/blood-center/blood-center-registration';

@Component({
  selector: 'app-blood-center-registration',
  templateUrl: './blood-center-registration.component.html',
  styleUrls: ['./blood-center-registration.component.css']
})
export class BloodCenterRegistrationComponent implements OnInit {

  registrationForm = new FormGroup({
    name : new FormControl<string>('', [Validators.required]),
    description : new FormControl<string>('', [Validators.required]),
    street : new FormControl<string>('', [Validators.required]),
    number : new FormControl<string>('', [Validators.required]),
    city : new FormControl<string>('', [Validators.required]),
    country : new FormControl<string>('', [Validators.required]),
    latitude : new FormControl<number>(0, [Validators.required]),
    longitude : new FormControl<number>(0, [Validators.required])
  })  

  constructor(private readonly bloodCenterService: BloodCenterService, private readonly router: Router) { }

  ngOnInit(): void {
  }

  get form(){
    return this.registrationForm.controls;
  }

  register = () =>
  {
    let bloodCenter: BloodCenterRegistration ={
      name : this.form.name.value ?? "",
      address: {
        street:this.form.street.value ?? "",
        number:this.form.number.value ?? "",
        city:this.form.city.value ?? "",
        country:this.form.country.value ?? "",
        latitude:this.form.latitude.value ?? 0,
        longitude: this.form.longitude.value ?? 0
      },
      description :this.form.description.value ?? "",
    };
    this.bloodCenterService.registerBloodCenter(bloodCenter).subscribe(_ =>{
        this.router.navigate(['']);
    });
  }
}
