import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PremadeAppointmentsComponent } from './premade-appointments/premade-appointments.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    PremadeAppointmentsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class PremadeAppointmentsModule { }
