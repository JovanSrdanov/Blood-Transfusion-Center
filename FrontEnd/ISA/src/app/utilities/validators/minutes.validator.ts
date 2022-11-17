import { AbstractControl } from '@angular/forms';

export function MinutesValidator(control: AbstractControl) {
  if (control.value < 0 || control.value > 59) {
    return { invalidMinutes: true };
  }
  return null;
}