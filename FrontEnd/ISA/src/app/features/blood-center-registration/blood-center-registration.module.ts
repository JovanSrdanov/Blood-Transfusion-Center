import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodCenterRegistrationComponent } from './blood-center-registration/blood-center-registration.component';
import { NavbarModule } from '../navbar/navbar.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    BloodCenterRegistrationComponent
  ],
  imports: [
    CommonModule,
    NavbarModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class BloodCenterRegistrationModule { }
