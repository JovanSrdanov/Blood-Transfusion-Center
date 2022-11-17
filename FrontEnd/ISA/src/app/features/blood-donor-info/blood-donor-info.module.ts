import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorInfoFormComponent } from './blood-donor-info-form/blood-donor-info-form.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MaterialModule } from 'src/app/material/material.module';
import { LoyaltyTableComponent } from './loyalty-table/loyalty-table.component';


@NgModule({
  declarations: [
    BloodDonorInfoFormComponent,
    LoyaltyTableComponent
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MaterialModule
  ]
})
export class BloodDonorInfoModule { }
