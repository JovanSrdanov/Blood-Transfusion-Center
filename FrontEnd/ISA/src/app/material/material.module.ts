import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';

const material = [
  MatCardModule,
  MatToolbarModule,
  MatButtonModule,
  MatGridListModule,
];

@NgModule({
  imports: [material],
  exports: [material],
})
export class MaterialModule {}
