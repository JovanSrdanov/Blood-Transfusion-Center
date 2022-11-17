import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { BloodDonorService } from 'src/app/http-services/blood-donor.service';
import { BloodDonorInfo } from 'src/app/model/blood-donor/blood-donor-info';
import { LoyaltyTableComponent } from '../loyalty-table/loyalty-table.component';

@Component({
  selector: 'app-blood-donor-info-form',
  templateUrl: './blood-donor-info-form.component.html',
  styleUrls: ['./blood-donor-info-form.component.css']
})
export class BloodDonorInfoFormComponent implements OnInit {
  
  registrationForm = new FormGroup({
    name: new FormControl<string>('', [Validators.required]),
    city: new FormControl<string>('', [Validators.required]),
    street: new FormControl<string>('', [Validators.required]),
    streetNumber: new FormControl<string>('', [Validators.required]),
    country: new FormControl<string>('', [Validators.required]),
    surname: new FormControl<string>('', [Validators.required]),
    email: new FormControl<string>('', [Validators.required]),
    phoneNumber: new FormControl<string>('', [Validators.required]),
    institution: new FormControl<string>('', [Validators.required]),
    occupation: new FormControl<string>('', [Validators.required]),
    jmbg: new FormControl<string>('', [Validators.required]),
    points: new FormControl<number>(0, [Validators.required])
  });

  currentDonor!: BloodDonorInfo;

  editable :boolean = false;
  iconType: string = "edit";
  eventsSubject: Subject<number> = new Subject<number>();

  constructor(private readonly bloodDonorService: BloodDonorService) { }

  ngOnInit(): void {
    this.bloodDonorService.fetchLoggedinDonor().subscribe((res) => {
      this.currentDonor = res;
      this.eventsSubject.next(res.points);
      this.populatedFields();
    });
  }

  enableEdit(): void {
    this.editable = !this.editable;
    if(this.editable) this.iconType = "clear icon";
    else {
      this.iconType = "edit"
      this.populatedFields();
    }
  }

  submitUpdate(): void {
    this.currentDonor.name = this.registrationForm.controls.name.value ?? "";
    this.currentDonor.address.city = this.registrationForm.controls.city.value ?? "";
    this.currentDonor.address.country = this.registrationForm.controls.country.value ?? "";
    this.currentDonor.address.number = this.registrationForm.controls.streetNumber.value ?? "";
    this.currentDonor.address.street = this.registrationForm.controls.street.value ?? "";
    this.currentDonor.surname = this.registrationForm.controls.surname.value ?? "";
    this.currentDonor.phoneNumber = this.registrationForm.controls.phoneNumber.value ?? "";
    this.currentDonor.jmbg = this.registrationForm.controls.jmbg.value ?? "";
    this.currentDonor.institution = this.registrationForm.controls.institution.value ?? "";
    this.currentDonor.occupation = this.registrationForm.controls.occupation.value ?? "";
    this.currentDonor.points = this.registrationForm.controls.points.value ?? 0;
    this.currentDonor.email = this.registrationForm.controls.occupation.value ?? "";
    console.log("Pre patcha ", this.currentDonor);

    this.bloodDonorService.updateLoggedinDonor(this.currentDonor).subscribe((res) => {
      console.log("Uspesan patch ", res);
      this.currentDonor = res;
      this.enableEdit();
    });
  }

  getErrorMessage(field: any) {
    if (field.hasError('required')) {
      return 'You must enter a value';
    }
    return '';
  }

  populatedFields(): void {
      this.registrationForm.controls.name.setValue(this.currentDonor.name);
      this.registrationForm.controls.city.setValue(this.currentDonor.address.city);
      this.registrationForm.controls.street.setValue(this.currentDonor.address.street);
      this.registrationForm.controls.country.setValue(this.currentDonor.address.country);
      this.registrationForm.controls.surname.setValue(this.currentDonor.surname);
      this.registrationForm.controls.phoneNumber.setValue(this.currentDonor.phoneNumber);
      this.registrationForm.controls.institution.setValue(this.currentDonor.institution);
      this.registrationForm.controls.occupation.setValue(this.currentDonor.occupation);
      this.registrationForm.controls.jmbg.setValue(this.currentDonor.jmbg);
      this.registrationForm.controls.streetNumber.setValue(this.currentDonor.address.number);
      this.registrationForm.controls.points.setValue(this.currentDonor.points);
      this.registrationForm.controls.email.setValue(this.currentDonor.email);
  }

}
