import { GoogleMapsModule } from '@angular/google-maps';
import { AppRoutingModule } from './routing/app-routing.module';
import { MaterialModule } from './material/material.module';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { AuthInterceptor } from './auth/auth.interceptor';
import { BloodDonorPageModule } from './pages/blood-donor-page/blood-donor-page.module';
import { StaffPageModule } from './pages/staff-page/staff-page.module';
import { SystemAdminPageModule } from './pages/system-admin-page/system-admin-page.module';
import { BrowserModule } from '@angular/platform-browser';
import { LoginPageModule } from './pages/login-page/login-page.module';
import { RegisterBloodDonorModule } from './pages/registration-page/register-blood-donor.module';
import { StaffProfileModalComponent } from './features/staff-profile/staff-profile-modal/staff-profile-modal.component';
import { GoogleMapComponent } from './GoogleMaps/google-map/google-map.component';
import { GoogleMapsModalComponent } from './features/staff-profile/google-maps-modal/google-maps-modal/google-maps-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    StaffProfileModalComponent,
    GoogleMapComponent,
    GoogleMapsModalComponent,
  ],
  imports: [
    //Nasi moduli za role
    BloodDonorPageModule,
    StaffPageModule,
    SystemAdminPageModule,
    LoginPageModule,
    RegisterBloodDonorModule,

    //Pomocni moduli
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    GoogleMapsModule,
  ],
  providers: [
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  exports: [GoogleMapComponent],
  bootstrap: [AppComponent],
})
export class AppModule {}
