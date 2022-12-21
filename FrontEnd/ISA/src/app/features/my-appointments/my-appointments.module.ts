import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyAppointmentsComponent } from './my-appointments/my-appointments.component';
import { MaterialModule } from 'src/app/material/material.module';


@NgModule({
  declarations: [
    MyAppointmentsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class MyAppointmentsModule { }
