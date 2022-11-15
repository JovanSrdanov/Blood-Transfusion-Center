import { MaterialModule } from './material/material.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { BloodAdminProfileModule } from './features/blood-admin-profile/blood-admin-profile.module';
import { BloodCenterRegistrationModule } from './features/blood-center-registration/blood-center-registration.module';
import { HttpClientModule } from '@angular/common/http';
import { NavbarModule } from './features/navbar/navbar.module';
import { RegisterUserModule } from './features/register-user/register-user.module';
import { MatSelectModule } from '@angular/material/select';
import { BloodAdminRegistrationComponent } from './features/blood-admin-registration/blood-admin-registration.component';
import { AssignBloodCenterComponent } from './features/assign-blood-center/assign-blood-center.component';

@NgModule({
  declarations: [AppComponent, AssignBloodCenterComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    LandingPageModule,
    FormsModule,
    RegisterUserModule,
    BloodAdminProfileModule,
    BloodCenterRegistrationModule,
    HttpClientModule,
    NavbarModule,
    MatSelectModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
