import { BloodAdminProfileComponent } from './features/blood-admin-profile/blood-admin-profile/blood-admin-profile.component';
import { LandingPageComponent } from './features/landing-page/landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BloodCenterRegistrationComponent } from './features/blood-center-registration/blood-center-registration/blood-center-registration.component';

const routes: Routes = [
  {path: '',    component: LandingPageComponent},
  { path: 'blood-admin-profile', component: BloodAdminProfileComponent },
  { path: 'blood-center', component: BloodCenterRegistrationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [LandingPageComponent, BloodAdminProfileComponent, BloodCenterRegistrationComponent];
