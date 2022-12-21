import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorAppointmentListComponent } from './blood-donor-appointment-list/blood-donor-appointment-list.component';
import { MaterialModule } from 'src/app/material/material.module';

@NgModule({
  declarations: [BloodDonorAppointmentListComponent],
  imports: [CommonModule, MaterialModule],
})
export class BloodDonorAppointmentListModule {}
