import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatFormFieldModule} from '@angular/material/form-field'; 
import {MatInputModule} from '@angular/material/input'; 

const material = [
  MatCardModule,
  MatToolbarModule,
  MatButtonModule,
  MatGridListModule, 
  MatFormFieldModule,
  MatInputModule
];

@NgModule({
  imports: [material],
  exports: [material],
})
export class MaterialModule {}
