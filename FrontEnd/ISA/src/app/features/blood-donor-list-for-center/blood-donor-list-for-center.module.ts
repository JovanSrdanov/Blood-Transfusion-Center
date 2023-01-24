import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorListForCenterComponent } from './blood-donor-list-for-center.component';
import { MaterialModule } from 'src/app/material/material.module';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    BloodDonorListForCenterComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule
  ]
})
export class BloodDonorListForCenterModule { }
