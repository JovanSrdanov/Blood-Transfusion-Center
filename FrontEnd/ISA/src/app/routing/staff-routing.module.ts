import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BloodDonorListComponent } from '../features/blood-donor-list/blood-donor-list.component';
import { StaffProfileComponent } from '../features/staff-profile/staff-profile/staff-profile.component';


const routes: Routes = [
  { path: 'blood-donor-list', component: BloodDonorListComponent },
  { path: 'staff-profile', component: StaffProfileComponent },
  { path: '', redirectTo: "staff-profile", pathMatch: "full" },
  { path: '**', redirectTo: "staff-profile", pathMatch: "full" },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes),

  ],
  exports: [RouterModule]

})
export class StaffRoutingModule { }
