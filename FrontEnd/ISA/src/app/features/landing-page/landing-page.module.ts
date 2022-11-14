import { BloodAdminProfileModule } from './../blood-admin-profile/blood-admin-profile.module';
import { MaterialModule } from '../../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NavbarModule } from '../navbar/navbar.module';

@NgModule({
  declarations: [LandingPageComponent],
  imports: [
    CommonModule,
    MaterialModule,
    BloodAdminProfileModule,
    NavbarModule,
  ],
})
export class LandingPageModule {}
