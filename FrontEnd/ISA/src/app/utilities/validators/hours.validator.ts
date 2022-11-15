import { AbstractControl } from '@angular/forms';

export function HoursValidator(control: AbstractControl) {
  if (control.value < 0 || control.value > 23) {
    return { invalidHours: true };
  }
  return null;
}