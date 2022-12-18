
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterBloodDonorComponent } from '../pages/registration-page/register-blood-donor/register-blood-donor.component';
import { BloodDonorPageComponent } from '../pages/blood-donor-page/blood-donor-page/blood-donor-page.component';
import { StaffPageComponent } from '../pages/staff-page/staff-page/staff-page.component';
import { SystemAdminPageComponent } from '../pages/system-admin-page/system-admin-page/system-admin-page.component';
import { RoleGuardService } from '../auth/guards/role-guard.service';
import { BloodDonorRoutingModule } from './blood-donor-routing.module';
import { SystemAdminRoutingModule } from './system-admin-routing.module';
import { StaffRoutingModule } from './staff-routing.module';
import { IncognitoGuard } from '../auth/guards/incognito-guard.service';
import { LoginPageComponent } from '../pages/login-page/login-page.component';

const routes: Routes = [
  {
    path: 'blood-donor', component: BloodDonorPageComponent, canActivate: [RoleGuardService],
    data: { expectedRole: "ROLE_BLOOD_DONOR" },
    loadChildren: () => import("./blood-donor-routing.module").then(m => BloodDonorRoutingModule)
  },

  {
    path: 'system-admin', component: SystemAdminPageComponent, canActivate: [RoleGuardService],
    data: { expectedRole: "ROLE_SYSTEM_ADMIN" },
    loadChildren: () => import("./system-admin-routing.module").then(m => SystemAdminRoutingModule)
  },

  {
    path: 'staff', component: StaffPageComponent, canActivate: [RoleGuardService],
    data: { expectedRole: "ROLE_STAFF" },
    loadChildren: () => import("./staff-routing.module").then(m => StaffRoutingModule)
  },

  { path: 'register-blood-donor', component: RegisterBloodDonorComponent, canActivate: [IncognitoGuard] },
  { path: 'login', component: LoginPageComponent, canActivate: [IncognitoGuard] },

  { path: '', redirectTo: "login", pathMatch: "full" },
  { path: '**', redirectTo: "login", pathMatch: "full" },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

  exports: [RouterModule],
})
export class AppRoutingModule { }

