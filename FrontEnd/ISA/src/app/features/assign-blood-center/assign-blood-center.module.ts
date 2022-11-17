import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignBloodCenterComponent } from './assign-blood-center.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    AssignBloodCenterComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports:[AssignBloodCenterComponent]
})
export class AssignBloodCenterModule { }
