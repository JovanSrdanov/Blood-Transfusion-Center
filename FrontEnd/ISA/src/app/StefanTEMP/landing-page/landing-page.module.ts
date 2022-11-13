import { BloodAdminProfileModule } from './../blood-admin-profile/blood-admin-profile.module';
import { MaterialModule } from './../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './landing-page/landing-page.component';

@NgModule({
  declarations: [LandingPageComponent],
  imports: [CommonModule, MaterialModule, BloodAdminProfileModule],
})
export class LandingPageModule {}
