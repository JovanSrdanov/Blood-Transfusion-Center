import { MaterialModule } from 'src/app/material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppointmentDetailsComponent } from './appointment-details/appointment-details.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [AppointmentDetailsComponent],
  imports: [CommonModule, MaterialModule, FormsModule],
})
export class AppointmentDetailsModule {}
