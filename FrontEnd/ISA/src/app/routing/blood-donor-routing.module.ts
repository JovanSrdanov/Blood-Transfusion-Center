import { NgModule } from '@angular/core';

import { BloodCenterTableComponent } from '../features/blood-center-view/blood-center-table/blood-center-table.component';
import { QuestionnaireComponent } from '../features/questionnaire/questionnaire/questionnaire.component';
import { RouterModule, Routes } from '@angular/router';
import { BloodDonorInfoFormComponent } from '../features/blood-donor-info/blood-donor-info-form/blood-donor-info-form.component';

const routes: Routes = [
  { path: 'questionnaire', component: QuestionnaireComponent },
  { path: 'blood-center-view', component: BloodCenterTableComponent },
  { path: 'blood-donor-info', component: BloodDonorInfoFormComponent },
  { path: '', redirectTo: 'blood-center-view', pathMatch: 'full' },
  { path: '**', redirectTo: 'blood-center-view', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BloodDonorRoutingModule {}
