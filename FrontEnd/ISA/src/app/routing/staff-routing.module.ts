import { CreateMedicalReportComponent } from './../features/create-medical-report/create-medical-report/create-medical-report.component';
import { AppointmentDetailsComponent } from './../features/appointment-details/appointment-details/appointment-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BloodDonorAppointmentListComponent } from '../features/blood-donor-appointment-list/blood-donor-appointment-list/blood-donor-appointment-list.component';
import { BloodDonorListComponent } from '../features/blood-donor-list/blood-donor-list.component';
import { StaffProfileComponent } from '../features/staff-profile/staff-profile/staff-profile.component';
import { BloodCenterCalendarComponent } from '../features/blood-center-calendar/blood-center-calendar/blood-center-calendar.component';
import { CreatePremadeAppointmentComponent } from '../features/create-premade-appointment/create-premade-appointment/create-premade-appointment/create-premade-appointment.component';

const routes: Routes = [
  {
    path: 'blood-donor-list',
    component: BloodDonorListComponent,
  },
  {
    //TODO Router link umesto navigate
    path: 'donor-appointments/:id',
    component: BloodDonorAppointmentListComponent,
  },
  {
    //Za cega ce ti donorId?
    path: 'appointment-details/:appointmentHistoryId/:donorId',
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
