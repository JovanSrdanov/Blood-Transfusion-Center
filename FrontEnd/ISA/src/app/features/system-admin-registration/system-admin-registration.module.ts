import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAdminRegistrationComponent } from './system-admin-registration/system-admin-registration.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    SystemAdminRegistrationComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class SystemAdminRegistrationModule { }
