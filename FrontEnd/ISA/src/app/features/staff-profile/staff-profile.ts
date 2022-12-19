import { ChangePasswordComponent } from './change-password/change-password.component';
import { StaffProfileComponent } from './staff-profile/staff-profile.component';
import { MaterialModule } from '../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarModule } from '../navbar/navbar.module';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [StaffProfileComponent, ChangePasswordComponent],
  imports: [
    CommonModule,
    MaterialModule,
    NavbarModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
  ],
})
export class StaffProfileModule { }
