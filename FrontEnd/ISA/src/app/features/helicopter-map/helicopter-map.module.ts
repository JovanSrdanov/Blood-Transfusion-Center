import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HelicopterMapComponent } from './helicopter-map/helicopter-map.component';
import { MaterialModule } from 'src/app/material/material.module';


@NgModule({
  declarations: [
    HelicopterMapComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class HelicopterMapModule { }
