import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyAppointmentsComponent } from './my-appointments/my-appointments.component';
import { MaterialModule } from 'src/app/material/material.module';
import { QRCodesAppointmentsComponent } from './qr-codes-appointments/qr-codes-appointments.component';
import { QRCodeModule } from 'angularx-qrcode';

@NgModule({
  declarations: [
    MyAppointmentsComponent,
    QRCodesAppointmentsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    QRCodeModule
  ]
})
export class MyAppointmentsModule { }
