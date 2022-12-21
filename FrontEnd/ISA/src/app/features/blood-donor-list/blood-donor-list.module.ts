import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorListComponent } from './blood-donor-list.component';
import { MaterialModule } from 'src/app/material/material.module';
import { DirectivesModule } from 'src/app/utilities/directives/directives/directives.module';

@NgModule({
  declarations: [BloodDonorListComponent],
  imports: [CommonModule, MaterialModule, DirectivesModule, RouterModule],
})
export class BloodDonorListModule {}
