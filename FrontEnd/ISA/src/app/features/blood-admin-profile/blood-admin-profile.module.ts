import { BloodAdminProfileComponent } from './blood-admin-profile/blood-admin-profile.component';
import { MaterialModule } from '../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarModule } from '../navbar/navbar.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [BloodAdminProfileComponent],
  imports: [CommonModule, MaterialModule, NavbarModule, FormsModule],
})
export class BloodAdminProfileModule {}
