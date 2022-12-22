import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { CreatePremadeAppointmentComponent } from './create-premade-appointment/create-premade-appointment/create-premade-appointment.component';



@NgModule({
  declarations: [


    CreatePremadeAppointmentComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class CreatePremadeAppointmentModule { }
