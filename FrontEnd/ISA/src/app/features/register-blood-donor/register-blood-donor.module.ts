import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterBloodDonorComponent } from './register-blood-donor/register-blood-donor.component';
import { MatInputModule } from '@angular/material/input';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MaterialModule } from 'src/app/material/material.module';
import { MatSliderModule } from '@angular/material/slider'
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';




@NgModule({

  declarations: [
    RegisterBloodDonorComponent
  ],
  imports: [
  CommonModule,
    MaterialModule,
    MatFormFieldModule,
    MatInputModule,
    MatSliderModule,
    MatSelectModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  exports: [
    CommonModule,
    MaterialModule,
    MatFormFieldModule,
    MatInputModule,
    MatSliderModule

  ],
})
export class RegisterBloodDonorModule { }
