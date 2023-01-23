import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-staff-profile-modal',
  templateUrl: './staff-profile-modal.component.html',
  styleUrls: ['./staff-profile-modal.component.css'],
})
export class StaffProfileModalComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
