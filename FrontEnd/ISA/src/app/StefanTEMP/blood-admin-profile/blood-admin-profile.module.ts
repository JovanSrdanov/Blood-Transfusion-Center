import { MaterialModule } from './../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodAdminProfileComponent } from './blood-admin-profile/blood-admin-profile.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [BloodAdminProfileComponent],
  imports: [CommonModule, MaterialModule, FormsModule],
})
export class BloodAdminProfileModule {}
