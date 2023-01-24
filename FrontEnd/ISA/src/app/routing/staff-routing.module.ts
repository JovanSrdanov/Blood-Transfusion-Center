import { CreateMedicalReportComponent } from './../features/create-medical-report/create-medical-report/create-medical-report.component';
import { AppointmentDetailsComponent } from './../features/appointment-details/appointment-details/appointment-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BloodDonorAppointmentListComponent } from '../features/blood-donor-appointment-list/blood-donor-appointment-list/blood-donor-appointment-list.component';
import { BloodDonorListComponent } from '../features/blood-donor-list/blood-donor-list.component';
import { StaffProfileComponent } from '../features/staff-profile/staff-profile/staff-profile.component';
import { BloodCenterCalendarComponent } from '../features/blood-center-calendar/blood-center-calendar/blood-center-calendar.component';
import { CreatePremadeAppointmentComponent } from '../features/create-premade-appointment/create-premade-appointment/create-premade-appointment/create-premade-appointment.component';
import { AppQrScanComponent } from '../features/app-qr-scan/app-qr-scan.component';
import { BloodDonorListForCenterComponent } from '../features/blood-donor-list-for-center/blood-donor-list-for-center.component';
import { BloodDonorVisitedListForCenterComponent } from '../features/blood-donor-visited-list-for-center/blood-donor-visited-list-for-center.component';
import { HelicopterMapComponent } from '../features/helicopter-map/helicopter-map/helicopter-map.component';
import { ApproveShipmentsComponent } from '../features/approve-shipments/approve-shipments/approve-shipments.component';

const routes: Routes = [
  {
    //TODO Router link umesto navigate
    path: 'donor-appointments/:id',
    component: BloodDonorAppointmentListComponent,
  },
  {
    path: 'blood-donor-list-scheduled',
    component: BloodDonorListForCenterComponent,
  },
  {
    path: 'blood-donor-list-visited',
    component: BloodDonorVisitedListForCenterComponent,
  },
  {
    path: 'appointment-details/:appointmentHistoryId',
    component: AppointmentDetailsComponent,
  },
  {
    path: 'create-report/:appointmentHistoryId',
    component: CreateMedicalReportComponent,
  },
  {
    path: 'create-premade-appointment',
    component: CreatePremadeAppointmentComponent,
  },
  {
    path: 'app-qr-scan',
    component: AppQrScanComponent,
  },
  {
    path: 'approve-shipments',
    component: ApproveShipmentsComponent,
  },
  {
    path: 'helicopter-map',
    component: HelicopterMapComponent,
  },
  { path: 'staff-profile', component: StaffProfileComponent },
  { path: 'calendar', component: BloodCenterCalendarComponent },
  { path: '', redirectTo: 'staff-profile', pathMatch: 'full' },
  { path: '**', redirectTo: 'staff-profile', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StaffRoutingModule { }
