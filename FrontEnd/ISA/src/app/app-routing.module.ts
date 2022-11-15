
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationComponent } from './features/blood-center-registration/blood-center-registration/blood-center-registration.component';
import { RegisterUserComponent } from './features/register-user/register-user/register-user.component';
import { NavbarComponent } from './features/navbar/navbar/navbar.component';
import { StaffRegistrationComponent } from './features/staff-registration/staff-registration.component';
import { AssignBloodCenterComponent } from './features/assign-blood-center/assign-blood-center.component';
import { StaffProfileComponent } from './features/staff-profile/staff-profile/staff-profile.component';

const routes: Routes = [
  {path:'navbar',component : NavbarComponent},
  { path: 'staff-profile', component: StaffProfileComponent },
  { path: 'blood-center', component: BloodCenterRegistrationComponent },
  { path: 'register-user', component: RegisterUserComponent },
  { path: 'staff-registration', component: StaffRegistrationComponent},
  { path: 'staff/assign-blood-center', component: AssignBloodCenterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [RegisterUserComponent ,NavbarComponent, StaffProfileComponent, BloodCenterRegistrationComponent];
