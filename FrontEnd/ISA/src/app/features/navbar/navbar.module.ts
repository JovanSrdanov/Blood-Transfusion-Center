import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    NavbarComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports:[NavbarComponent]
})
export class NavbarModule { }
