import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

const MaterialComponents = [MatCardModule];

@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents],
})
export class MaterialModule {}
