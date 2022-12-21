import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAdminPasswordUpdateComponent } from './system-admin-password-update/system-admin-password-update.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    SystemAdminPasswordUpdateComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class SystemAdminPasswordUpdateModule { }
