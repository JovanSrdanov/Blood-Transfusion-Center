import { Component } from '@angular/core';

@Component({
  selector: 'app-qr-codes-appointments',
  templateUrl: './qr-codes-appointments.component.html',
  styleUrls: ['./qr-codes-appointments.component.css']
})
export class QRCodesAppointmentsComponent {
  public myAngularxQrCode: string = "";
  constructor() {
    // assign a value
    this.myAngularxQrCode = 'Your QR code data string';
  }

}
