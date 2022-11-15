
import { MaterialModule } from './material/material.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BloodAdminProfileModule } from './features/blood-admin-profile/blood-admin-profile.module';
import { BloodCenterRegistrationModule } from './features/blood-center-registration/blood-center-registration.module';
import { HttpClientModule } from '@angular/common/http';
import { NavbarModule } from './features/navbar/navbar.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    NavbarModule,
    BloodAdminProfileModule,
    BloodCenterRegistrationModule,
    HttpClientModule,
    NavbarModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
