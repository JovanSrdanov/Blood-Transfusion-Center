import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { ProfileService } from 'src/app/http-services/staff/profile.service';
import { FreeSlots } from 'src/app/model/appointment/free-slots';
import { PremadeAppointment } from 'src/app/model/appointment/premade-appointment';
import { PremadeFreeSlots } from 'src/app/model/appointment/premade-free-slots';
import { StaffFreeSlotsInfo } from 'src/app/model/staff/staff-free-slots-info';

@Component({
  selector: 'app-create-premade-appointment',
  templateUrl: './create-premade-appointment.component.html',
  styleUrls: ['./create-premade-appointment.component.css']
})
export class CreatePremadeAppointmentComponent implements OnInit {

  isEditable = true;

  freeSlotsForm = new FormGroup({
    selectedStaff: new FormControl(''),
    date: new FormControl<string>(''),
    duration: new FormControl<number>(15),
  });

  timeRange = new FormGroup({
    selectedTimeRange: new FormControl('')
  });

  
  staffList: StaffFreeSlotsInfo[] = [];
  freeSlots: FreeSlots[] = [];

  constructor(private readonly router: Router, private _formBuilder: FormBuilder,
    private readonly staffService: ProfileService,
    private readonly appointmentService: AppointmentServiceService ) { }

  ngOnInit(): void {
    this.staffService.getStaffInfoForFreeSlots().subscribe((res) => {
      this.staffList = res;
    });
  }

  get formSlots() {
    return this.freeSlotsForm.controls;
  }

  get predefineForm() {
    return this.timeRange.controls;
  }

  getFreeSlots() {
    let list: any = this.formSlots.selectedStaff.value;
    let date: string = this.formSlots.date.value ?? '';
    let duration: number = this.formSlots.duration.value ?? 15;
    /* console.log(list);
    console.log(date);
    console.log(duration); */

    let freeStaffSlotsDto: PremadeFreeSlots = {
      staffIds: this.formSlots.selectedStaff.value,
      date: this.formSlots.date.value ?? '',
      duration: this.formSlots.duration.value ?? 15
    }

    freeStaffSlotsDto.date += 'T00:00:00';

    this.appointmentService.availableSlots(freeStaffSlotsDto).subscribe((res) => {
      this.freeSlots = res;
    })

  }

  predefineAppointment() {
    let premadeAppointmentDto: PremadeAppointment = {
      dateRange: this.predefineForm.selectedTimeRange.value,
      staffIds: this.formSlots.selectedStaff.value
    }
    premadeAppointmentDto.dateRange = premadeAppointmentDto.dateRange[0];

    console.log(premadeAppointmentDto);
    this.appointmentService.premadeAppointment(premadeAppointmentDto).subscribe(res => {
      console.log(res);
    });
  }

}
