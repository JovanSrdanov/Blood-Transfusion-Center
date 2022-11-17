import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DebounceKeyupDirective } from '../../validators/debounce/debounce-key-up.directive';



@NgModule({
  declarations: [DebounceKeyupDirective],
  imports: [
    CommonModule
  ]
})
export class DirectivesModule { }
