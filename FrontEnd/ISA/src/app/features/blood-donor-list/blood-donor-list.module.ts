import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorListComponent } from './blood-donor-list.component';
import { MaterialModule } from 'src/app/material/material.module';

@NgModule({
  declarations: [BloodDonorListComponent],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule
  ],
})
export class BloodDonorListModule {}
