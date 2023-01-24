import { MatDialogModule } from '@angular/material/dialog';
import { MaterialModule } from 'src/app/material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateMedicalReportComponent } from './create-medical-report/create-medical-report.component';
import { FormsModule } from '@angular/forms';
import { NoteToDoctorModalComponent } from './note-to-doctor-modal/note-to-doctor-modal.component';

@NgModule({
  declarations: [CreateMedicalReportComponent, NoteToDoctorModalComponent],
  imports: [CommonModule, MaterialModule, FormsModule, MatDialogModule],
})
export class CreateMedicalReportModule {}
