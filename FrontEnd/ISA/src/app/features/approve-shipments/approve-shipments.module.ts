import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApproveShipmentsComponent, SendingShipmentDialog } from './approve-shipments/approve-shipments.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    ApproveShipmentsComponent,
    SendingShipmentDialog
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class ApproveShipmentsModule { }
