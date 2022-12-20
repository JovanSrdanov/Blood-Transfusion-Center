import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from './login-page.component';
import { MaterialModule } from 'src/app/material/material.module';
import { BloodCenterViewModule } from 'src/app/features/blood-center-view/blood-center-view.module';

@NgModule({
  declarations: [LoginPageComponent],
  imports: [CommonModule, MaterialModule, BloodCenterViewModule],
})
export class LoginPageModule {}
