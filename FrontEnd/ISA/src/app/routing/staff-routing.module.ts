import { AppointmentDetailsComponent } from './../features/appointment-details/appointment-details/appointment-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BloodDonorAppointmentListComponent } from '../features/blood-donor-appointment-list/blood-donor-appointment-list/blood-donor-appointment-list.component';
import { BloodDonorListComponent } from '../features/blood-donor-list/blood-donor-list.component';
import { StaffProfileComponent } from '../features/staff-profile/staff-profile/staff-profile.component';

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
    path: 'appointment-details/:appointmentId/:donorId',
    component: AppointmentDetailsComponent,
  },
  { path: 'staff-profile', component: StaffProfileComponent },
  { path: '', redirectTo: 'staff-profile', pathMatch: 'full' },
  { path: '**', redirectTo: 'staff-profile', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StaffRoutingModule {}
