import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { ScheduleCustomDto } from 'src/app/model/appointment/scheduleCustomDto';
import { TimeWarpper } from 'src/app/model/appointment/timeWarpper';

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css']
})
export class CreateAppointmentComponent implements OnInit {

  time: TimeWarpper = {time:"2022-12-25T09:50:00"};

  dateFormCtrl = new FormControl();

  loginForm = new FormGroup({
    time2: new FormControl<string>(''),
  });

  dto: ScheduleCustomDto | null = null;

  formPremade = new FormControl('');
  premadeTable: any[] = [];

  constructor(private readonly router: Router, private readonly appointmentService: AppointmentServiceService) { }

  ngOnInit(): void {
  }

  test(): void {
    this.time = {time: "2022-12-25T09:50:00"};

    this.appointmentService.getCustomAvailable(this.time).subscribe((res) => {
      this.premadeTable = res;
    });
  }

  schedule(): void {
    console.log(this.formPremade.value)

    this.dto = {
      time : this.dateFormCtrl.value,
      staffId : this.formPremade.value?.at(0)
    }

    this.appointmentService.scheduleCustom(this.dto).subscribe((res) => {
      console.log(res);
    }); 
  }

}
