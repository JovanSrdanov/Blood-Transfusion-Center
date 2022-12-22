// https://mattlewis92.github.io/angular-calendar/#/kitchen-sink
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BloodCenterCalendarComponent } from './blood-center-calendar/blood-center-calendar.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    BloodCenterCalendarComponent
  ],
  imports: [
    CommonModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
     NgbModule
  ]
})
export class BloodCenterCalendarModule { }
