import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisitingHistoryComponent } from './visiting-history/visiting-history.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    VisitingHistoryComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class VisitingHistoryModule { }
