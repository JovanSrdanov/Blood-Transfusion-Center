import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAdminPageComponent } from './system-admin-page/system-admin-page.component';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationModule } from 'src/app/features/blood-center-registration/blood-center-registration.module';
import { StaffRegistrationModule } from 'src/app/features/staff-registration/staff-registration.module';
import { AssignBloodCenterModule } from 'src/app/features/assign-blood-center/assign-blood-center.module';
@NgModule({
  declarations: [
    SystemAdminPageComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    BloodCenterRegistrationModule,
    StaffRegistrationModule,
    AssignBloodCenterModule
  ]
})
export class SystemAdminPageModule { }
