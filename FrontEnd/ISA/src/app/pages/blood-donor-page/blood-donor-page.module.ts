import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorPageComponent } from './blood-donor-page/blood-donor-page.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { QuestionnaireModule } from 'src/app/features/questionnaire/questionnaire.module';
import { BloodCenterViewModule } from 'src/app/features/blood-center-view/blood-center-view.module';
import { BloodDonorInfoModule } from 'src/app/features/blood-donor-info/blood-donor-info.module';
import { MaterialModule } from 'src/app/material/material.module';
import { CreateAppointmentModule } from 'src/app/features/create-appointment/create-appointment.module';
import { MyAppointmentsModule } from 'src/app/features/my-appointments/my-appointments.module';
import { PremadeAppointmentsModule } from 'src/app/features/premade-appointments/premade-appointments.module';
import { VisitingHistoryModule } from 'src/app/features/visiting-history/visiting-history.module';


@NgModule({
  declarations: [
    BloodDonorPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    QuestionnaireModule,
    BloodCenterViewModule,
    BloodDonorInfoModule,
    CreateAppointmentModule,
    MyAppointmentsModule,
    PremadeAppointmentsModule,
    VisitingHistoryModule,
    MaterialModule


  ]
})
export class BloodDonorPageModule { }
