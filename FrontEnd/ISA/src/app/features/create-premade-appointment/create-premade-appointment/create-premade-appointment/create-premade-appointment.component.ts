import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { PremadePost } from 'src/app/model/appointment/premadePost';
import { PremadeRequest } from 'src/app/model/appointment/premadeReqest';
import { PremadeTime } from 'src/app/model/appointment/premadeTime';
import { StaffPremade } from 'src/app/model/staff/staff-premade';

@Component({
  selector: 'app-create-premade-appointment',
  templateUrl: './create-premade-appointment.component.html',
  styleUrls: ['./create-premade-appointment.component.css']
})
export class CreatePremadeAppointmentComponent implements OnInit {

  dateFormCtrl = new FormControl(new Date());

  dto: PremadeRequest | null = null;
  scheduleDto: PremadePost | null = null;

  premadeTable: PremadeTime[] = [];

  toppings = new FormControl('');
  toppingList: StaffPremade[] = [];

  formPremade = new FormControl('');

  loginForm = new FormGroup({
    Minutes: new FormControl<number>(0),

  });

  get mins() {
    return this.loginForm.controls.Minutes.value;
  }

  constructor(private readonly router: Router, private readonly appointmentService: AppointmentServiceService) { }
  ngOnInit(): void {
    this.staffList();

  }


  generate(): void {
    //console.table("aaa " + this.dateFormCtrl.value)
    //this.appointmentService.getStaff().subscribe((res) => console.log(res));
    //this.appointmentService.getPremadeList(this.dto).subscribe((res) => console.log(res));
    //this.appointmentService.premadeAppointment(this.dto2).subscribe((res) => console.log(res));
    //console.log(this.toppings.value);
    //console.log(this.formPremade.value);

    this.showAvailable();
  }

  schedule(): void {
    this.scheduleDto = {
      staffIds: this.toppings.value ?? '',
      dateRange: this.formPremade.value?.at(0)
    }

    this.appointmentService.premadeAppointment(this.scheduleDto).subscribe((res) => {
      console.log(res);
      this.staffList();
      this.toppingList = [];
      window.location.reload();
    }
    );

    this.staffList();
  }




  showAvailable(): void {


    this.dto = {
      staffIds: this.toppings.value ?? '',
      date: this.dateFormCtrl.value,
      duration: this.mins ?? 0
    }

    this.appointmentService.getPremadeList(this.dto).subscribe((res) => {
      this.premadeTable = res;
    });
  }

  staffList(): void {
    this.appointmentService.getStaff().subscribe((res) => {
      this.toppingList = res;
    });
  }

}
