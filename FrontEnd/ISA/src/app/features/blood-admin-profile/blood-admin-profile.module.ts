import { MaterialModule } from '../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodAdminProfileComponent } from './blood-admin-profile/blood-admin-profile.component';
import { NavbarModule } from '../navbar/navbar.module';

@NgModule({
  declarations: [BloodAdminProfileComponent],
  imports: [
    CommonModule,
    MaterialModule,
    NavbarModule
    ]
})
export class BloodAdminProfileModule {}
