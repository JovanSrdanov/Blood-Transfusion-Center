import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BloodCenterService } from 'src/app/http-services/blood-center.service';
import { BloodCenterRegistration } from 'src/app/model/blood-center/blood-center-registration';
import { HoursValidator } from 'src/app/utilities/validators/hours.validator';
import { MinutesValidator } from 'src/app/utilities/validators/minutes.validator';

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
    longitude : new FormControl<number>(0, [Validators.required]),
    startHours : new FormControl<number>(0, [Validators.required, HoursValidator]),
    startMinutes : new FormControl<number>(0, [Validators.required, MinutesValidator]),
    endHours : new FormControl<number>(0, [Validators.required, HoursValidator]),
    endMinutes : new FormControl<number>(0, [Validators.required, MinutesValidator])
  })  

  addressAvailable: boolean = true;
  private _disableSubmit: boolean = !this.addressAvailable || this.registrationForm.invalid;

  constructor(private readonly bloodCenterService: BloodCenterService, private readonly router: Router) { }

  get form(){
    return this.registrationForm.controls;
  }

  get disableSubmit() {
    return  !this.addressAvailable || this.registrationForm.invalid;
  }

  ngOnInit(): void {
    this.onFormChange();
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
      workingHours:{
        startHours: this.form.startHours.value ?? 0,
        startMinutes: this.form.startMinutes.value ?? 0,
        endHours: this.form.endHours.value ?? 0,
        endMinutes: this.form.endMinutes.value ?? 0,

      }
    };

    this.bloodCenterService.registerBloodCenter(bloodCenter).subscribe({
        next: _ => { this.router.navigate([''])},
        error: _ => {
          this.addressAvailable = false;
        }
    });

  }

    onFormChange = ():void => {
      this.registrationForm.valueChanges.subscribe(_ => {
        this.addressAvailable = true;
      })
    }
}
