import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodDonorPageComponent } from './blood-donor-page/blood-donor-page.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { QuestionnaireModule } from 'src/app/features/questionnaire/questionnaire.module';
import { BloodCenterViewModule } from 'src/app/features/blood-center-view/blood-center-view.module';
import { BloodDonorInfoModule } from 'src/app/features/blood-donor-info/blood-donor-info.module';

@NgModule({
  declarations: [
    BloodDonorPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    QuestionnaireModule,
    BloodCenterViewModule
    , BloodDonorInfoModule


  ]
})
export class BloodDonorPageModule { }
