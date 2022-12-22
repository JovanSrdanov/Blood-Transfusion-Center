import { CreateMedicalReportModule } from './../../features/create-medical-report/create-medical-report.module';
import { AppointmentDetailsModule } from './../../features/appointment-details/appointment-details.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaffPageComponent } from './staff-page/staff-page.component';
import { RouterModule, Routes } from '@angular/router';
import { BloodDonorListModule } from 'src/app/features/blood-donor-list/blood-donor-list.module';
import { StaffProfileModule } from 'src/app/features/staff-profile/staff-profile';
import { MaterialModule } from 'src/app/material/material.module';
import { BloodDonorAppointmentListModule } from 'src/app/features/blood-donor-appointment-list/blood-donor-appointment-list.module';
import { BloodCenterCalendarModule } from 'src/app/features/blood-center-calendar/blood-center-calendar.module';
import { CreatePremadeAppointmentModule } from 'src/app/features/create-premade-appointment/create-premade-appointment.module';


@NgModule({
  declarations: [StaffPageComponent],
  imports: [
    CommonModule,
    RouterModule,
    BloodDonorListModule,
    BloodDonorAppointmentListModule,
    AppointmentDetailsModule,
    CreateMedicalReportModule,
    StaffProfileModule,
    MaterialModule,
    BloodCenterCalendarModule,
    CreatePremadeAppointmentModule
  ],
})
export class StaffPageModule { }
