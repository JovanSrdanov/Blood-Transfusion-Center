import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarModule } from '../navbar/navbar.module';
import { MaterialModule } from 'src/app/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BloodAdminRegistrationComponent } from './blood-admin-registration.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    BloodAdminRegistrationComponent
  ],
  imports: [
    CommonModule,
    NavbarModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class BloodAdminRegistrationModule { }
