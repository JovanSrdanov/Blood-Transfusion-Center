import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodCenterTableComponent } from './blood-center-table/blood-center-table.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    BloodCenterTableComponent
  ],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatSortModule,
    MatTableModule,
    MatInputModule,
    FormsModule
  ]
})
export class BloodCenterViewModule { }
