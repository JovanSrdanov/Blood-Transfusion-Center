import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { StaffProfileModule } from './features/staff-profile/staff-profile';
import { BloodCenterRegistrationModule } from './features/blood-center-registration/blood-center-registration.module';
import { HttpClientModule } from '@angular/common/http';
import { NavbarModule } from './features/navbar/navbar.module';
import { RegisterBloodDonorModule } from './features/register-blood-donor/register-blood-donor.module';
import { MatSelectModule } from '@angular/material/select';
import { StaffRegistrationModule } from './features/staff-registration/staff-registration.module';
import { BrowserModule } from '@angular/platform-browser';
import { MatCheckboxModule } from '@angular/material/checkbox';
import {MatFormFieldModule} from '@angular/material/form-field';

import { QuestionnaireModule } from './features/questionnaire/questionnaire.module';
import { BloodCenterViewModule } from './features/blood-center-view/blood-center-view.module';
import { BloodDonorInfoModule } from './features/blood-donor-info/blood-donor-info.module';
import { ChangePasswordComponent } from './features/staff-profile/change-password/change-password.component';
import { AssignBloodCenterModule } from './features/assign-blood-center/assign-blood-center.module';
import { BloodDonorListModule } from './features/blood-donor-list/blood-donor-list.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    MatCheckboxModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    StaffRegistrationModule,
    FormsModule,
    RegisterBloodDonorModule,
    BloodCenterRegistrationModule,
    HttpClientModule,
    NavbarModule,
    MatSelectModule,
    QuestionnaireModule,
    BloodCenterViewModule,
    BloodDonorInfoModule,
    AssignBloodCenterModule,
    BloodDonorListModule
  ],
  providers: [],
})
export class AppModule { }
