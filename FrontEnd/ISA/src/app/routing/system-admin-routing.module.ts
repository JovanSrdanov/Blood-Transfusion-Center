import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignBloodCenterComponent } from '../features/assign-blood-center/assign-blood-center.component';
import { BloodCenterRegistrationComponent } from '../features/blood-center-registration/blood-center-registration/blood-center-registration.component';
import { BloodCenterTableComponent } from '../features/blood-center-view/blood-center-table/blood-center-table.component';
import { StaffRegistrationComponent } from '../features/staff-registration/staff-registration.component';
import { SystemAdminPasswordUpdateComponent } from '../features/system-admin-password-update/system-admin-password-update/system-admin-password-update.component';
import { UpdatedPasswordGuardService as UpdatedPasswordGuard } from '../auth/guards/updated-password-guard.service';
import { InverseUpdatedPasswordGuardService as InverseUpdatedPasswordGuard } from '../auth/guards/inverse-updated-password-guard.service';
import { SystemAdminRegistrationComponent } from '../features/system-admin-registration/system-admin-registration/system-admin-registration.component';

const routes: Routes = [
  { path: 'register-blood-center', component: BloodCenterRegistrationComponent, canActivate : [UpdatedPasswordGuard] },
  { path: 'assign-blood-center', component: AssignBloodCenterComponent, canActivate : [UpdatedPasswordGuard] },
  { path: 'register-staff', component: StaffRegistrationComponent, canActivate : [UpdatedPasswordGuard]},
  { path: 'blood-center-view', component: BloodCenterTableComponent, canActivate : [UpdatedPasswordGuard]},
  { path: 'system-admin-registration', component: SystemAdminRegistrationComponent, canActivate : [UpdatedPasswordGuard]},
  { path: 'first-password-update', component: SystemAdminPasswordUpdateComponent, canActivate : [InverseUpdatedPasswordGuard] },
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
