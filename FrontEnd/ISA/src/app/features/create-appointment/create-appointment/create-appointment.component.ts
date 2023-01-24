import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, FormBuilder, Validators} from '@angular/forms';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { ScheduleCustom } from 'src/app/model/schedule-custom';
import { TimeDto } from 'src/app/model/time';

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css']
})
export class CreateAppointmentComponent implements OnInit {
  isEditable = true;

  createCustomForm = new FormGroup({
    date: new FormControl<string>(''),
  });

  selectedForm = new FormGroup({
    selectedCustom: new FormControl(''),
    selectedPremade: new FormControl(''),
  });

  availableCustom: any[] = [];
  availablePredefined: any[] = [];


  constructor(private _formBuilder: FormBuilder,
    private readonly appointmentService: AppointmentServiceService) {}

  ngOnInit(): void {
  }

  get form1() {
    return this.createCustomForm.controls;
  }

  get form2() {
    return this.selectedForm.controls;
  }

  test() {
    let timeDto: TimeDto = {
      time: this.form1.date.value + ":00" ?? ''
    }

    this.appointmentService.availableCustom(timeDto).subscribe(res => {
      this.availableCustom = res;
    })

    this.appointmentService.availablePredefinedInTime(timeDto).subscribe(res => {
      this.availablePredefined = res;
    })
  }

  scheduleCustom() {
    let dto: ScheduleCustom = {
      time: this.form1.date.value + ":00" ?? '',
      staffId: this.form2.selectedCustom.value ?? ''
    }

    dto.staffId = dto.staffId[0];

    console.log(dto);

    this.appointmentService.scheduleCustom(dto).subscribe(res => {
      console.log(res);
    })
  }

  schedulepredefined() {
    let appId:string = this.form2.selectedPremade.value ?? 'bb';
    console.log(appId);
    this.appointmentService.schedule(appId).subscribe(res => {
      console.log(res);
    });
  }

}
