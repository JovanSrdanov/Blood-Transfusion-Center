import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignBloodCenterComponent } from '../features/assign-blood-center/assign-blood-center.component';
import { BloodCenterRegistrationComponent } from '../features/blood-center-registration/blood-center-registration/blood-center-registration.component';
import { BloodCenterTableComponent } from '../features/blood-center-view/blood-center-table/blood-center-table.component';
import { StaffRegistrationComponent } from '../features/staff-registration/staff-registration.component';

const routes: Routes = [
  { path: 'register-blood-center', component: BloodCenterRegistrationComponent },
  { path: 'assign-blood-center', component: AssignBloodCenterComponent },
  { path: 'register-staff', component: StaffRegistrationComponent },
  { path: 'blood-center-view', component: BloodCenterTableComponent },
  { path: '', redirectTo: "blood-center-view", pathMatch: "full" },
  { path: '**', redirectTo: "blood-center-view", pathMatch: "full" },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes),

  ],
  exports: [RouterModule]

})
export class SystemAdminRoutingModule { }
