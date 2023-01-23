import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorVisitedListForCenterComponent } from './blood-donor-visited-list-for-center.component';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    BloodDonorVisitedListForCenterComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule

  ]
})
export class BloodDonorVisitedListForCenterModule { }
