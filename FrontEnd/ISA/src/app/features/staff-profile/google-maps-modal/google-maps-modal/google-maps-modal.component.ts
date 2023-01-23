import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, NgModule } from '@angular/core';

@Component({
  selector: 'app-google-maps-modal',
  templateUrl: './google-maps-modal.component.html',
  styleUrls: ['./google-maps-modal.component.css'],
})
export class GoogleMapsModalComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
