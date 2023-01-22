import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyAppointmentsComponent } from './my-appointments/my-appointments.component';
import { MaterialModule } from 'src/app/material/material.module';
import { QRCodeDialog, QRCodesAppointmentsComponent } from './qr-codes-appointments/qr-codes-appointments.component';
import { QRCodeModule } from 'angularx-qrcode';


@NgModule({
  declarations: [
    MyAppointmentsComponent,
    QRCodesAppointmentsComponent,
    QRCodeDialog
  ],
  imports: [
    CommonModule,
    MaterialModule,
    QRCodeModule,
    MaterialModule
  ]
})
export class MyAppointmentsModule { }
