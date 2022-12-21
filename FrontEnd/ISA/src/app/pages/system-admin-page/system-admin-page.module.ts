import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAdminPageComponent } from './system-admin-page/system-admin-page.component';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationModule } from 'src/app/features/blood-center-registration/blood-center-registration.module';
import { StaffRegistrationModule } from 'src/app/features/staff-registration/staff-registration.module';
import { AssignBloodCenterModule } from 'src/app/features/assign-blood-center/assign-blood-center.module';
import { MaterialModule } from 'src/app/material/material.module';
import { SystemAdminPasswordUpdateModule } from 'src/app/features/system-admin-password-update/system-admin-password-update.module';
@NgModule({
  declarations: [
    SystemAdminPageComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    BloodCenterRegistrationModule,
    StaffRegistrationModule,
    AssignBloodCenterModule,
    SystemAdminPasswordUpdateModule
  ]
})
export class SystemAdminPageModule { }
