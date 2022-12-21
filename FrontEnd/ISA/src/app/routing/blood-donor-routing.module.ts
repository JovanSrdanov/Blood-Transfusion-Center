import { NgModule } from '@angular/core';

import { BloodCenterTableComponent } from '../features/blood-center-view/blood-center-table/blood-center-table.component';
import { QuestionnaireComponent } from '../features/questionnaire/questionnaire/questionnaire.component';
import { RouterModule, Routes } from '@angular/router';
import { BloodDonorInfoFormComponent } from '../features/blood-donor-info/blood-donor-info-form/blood-donor-info-form.component';
import { CreateAppointmentComponent } from '../features/create-appointment/create-appointment/create-appointment.component';
import { MyAppointmentsComponent } from '../features/my-appointments/my-appointments/my-appointments.component';
import { VisitingHistoryComponent } from '../features/visiting-history/visiting-history/visiting-history.component';
import { PremadeAppointmentsComponent } from '../features/premade-appointments/premade-appointments/premade-appointments.component';

const routes: Routes = [
  { path: 'questionnaire', component: QuestionnaireComponent },
  { path: 'blood-center-view', component: BloodCenterTableComponent },
  { path: 'blood-donor-info', component: BloodDonorInfoFormComponent },
  { path: 'create-appointment', component: CreateAppointmentComponent },

  { path: 'visiting-history', component: VisitingHistoryComponent },
  { path: 'my-appointments', component: MyAppointmentsComponent },
  { path: 'premade-appointments', component: PremadeAppointmentsComponent },


  { path: '', redirectTo: 'blood-center-view', pathMatch: 'full' },
  { path: '**', redirectTo: 'blood-center-view', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BloodDonorRoutingModule { }
