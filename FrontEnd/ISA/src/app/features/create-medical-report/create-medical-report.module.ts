import { MaterialModule } from 'src/app/material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateMedicalReportComponent } from './create-medical-report/create-medical-report.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [CreateMedicalReportComponent],
  imports: [CommonModule, MaterialModule, FormsModule],
})
export class CreateMedicalReportModule {}
