import { BloodAdminProfileComponent } from './features/blood-admin-profile/blood-admin-profile/blood-admin-profile.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationComponent } from './features/blood-center-registration/blood-center-registration/blood-center-registration.component';
import { RegisterUserComponent } from './features/register-user/register-user/register-user.component';
import { NavbarComponent } from './features/navbar/navbar/navbar.component';
import { BloodAdminRegistrationComponent } from './features/blood-admin-registration/blood-admin-registration.component';
import { AssignBloodCenterComponent } from './features/assign-blood-center/assign-blood-center.component';

const routes: Routes = [
  {path:'navbar',component : NavbarComponent},
  { path: 'blood-admin-profile', component: BloodAdminProfileComponent },
  { path: 'blood-center', component: BloodCenterRegistrationComponent },
  { path: 'register-user', component: RegisterUserComponent },
  { path: 'blood-admin', component: BloodAdminRegistrationComponent},
  { path: 'blood-admin/assign-blood-center', component: AssignBloodCenterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [RegisterUserComponent ,NavbarComponent, BloodAdminProfileComponent, BloodCenterRegistrationComponent];
