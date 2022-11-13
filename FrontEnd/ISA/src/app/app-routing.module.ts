import { BloodAdminProfileComponent } from './StefanTEMP/blood-admin-profile/blood-admin-profile/blood-admin-profile.component';
import { LandingPageComponent } from './StefanTEMP/landing-page/landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
  },
  { path: 'blood-admin-profile', component: BloodAdminProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [LandingPageComponent];
