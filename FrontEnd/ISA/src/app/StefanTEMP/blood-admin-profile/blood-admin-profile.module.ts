import { MaterialModule } from './../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodAdminProfileComponent } from './blood-admin-profile/blood-admin-profile.component';

@NgModule({
  declarations: [BloodAdminProfileComponent],
  imports: [CommonModule, MaterialModule],
})
export class BloodAdminProfileModule {}
