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
    NavbarModule
    ]
})
export class LandingPageModule {}
