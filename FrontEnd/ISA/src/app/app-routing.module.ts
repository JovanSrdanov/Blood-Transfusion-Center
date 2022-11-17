
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationComponent } from './features/blood-center-registration/blood-center-registration/blood-center-registration.component';
import { RegisterBloodDonorComponent } from './features/register-blood-donor/register-blood-donor/register-blood-donor.component';
import { NavbarComponent } from './features/navbar/navbar/navbar.component';
import { StaffRegistrationComponent } from './features/staff-registration/staff-registration.component';
import { AssignBloodCenterComponent } from './features/assign-blood-center/assign-blood-center.component';
import { StaffProfileComponent } from './features/staff-profile/staff-profile/staff-profile.component';
import { QuestionnaireComponent } from './features/questionnaire/questionnaire/questionnaire.component';
import { BloodCenterTableComponent } from './features/blood-center-view/blood-center-table/blood-center-table.component';
import { BloodDonorInfoFormComponent } from './features/blood-donor-info/blood-donor-info-form/blood-donor-info-form.component';
import { BloodDonorListComponent } from './features/blood-donor-list/blood-donor-list.component';

const routes: Routes = [
  {path:'navbar',component : NavbarComponent},
  { path: 'staff-profile', component: StaffProfileComponent },
  { path: 'blood-center', component: BloodCenterRegistrationComponent },
  { path: 'register-blood-donor', component: RegisterBloodDonorComponent },
  { path: 'staff-registration', component: StaffRegistrationComponent},
  { path: 'staff/assign-blood-center', component: AssignBloodCenterComponent },
   { path: 'questionnaire', component: QuestionnaireComponent},
   { path: 'blood-center-view', component: BloodCenterTableComponent},
   { path: 'blood-donor-info', component: BloodDonorInfoFormComponent},
  { path: 'blood-donor-list', component: BloodDonorListComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [RegisterBloodDonorComponent ,NavbarComponent, 
  StaffProfileComponent, BloodCenterRegistrationComponent, BloodCenterTableComponent, BloodDonorInfoFormComponent];
