import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';

@Component({
  selector: 'app-note-to-doctor-modal',
  templateUrl: './note-to-doctor-modal.component.html',
  styleUrls: ['./note-to-doctor-modal.component.css'],
})
export class NoteToDoctorModalComponent {
  oldNote: string = '';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.oldNote = this.data.note;
  }
}
