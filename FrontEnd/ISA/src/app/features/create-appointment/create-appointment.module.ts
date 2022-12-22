import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateAppointmentComponent } from './create-appointment/create-appointment.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    CreateAppointmentComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class CreateAppointmentModule { }
