import { AppointmentCancelation } from './../../../model/appointment/appointment-cancelation';
import { AppointmentSchedulingHistoryService } from 'src/app/http-services/appointment-scheduling-history.service';
import { QuestionnaireService } from './../../../http-services/questionnaire.service';
import { Questionnaire } from 'src/app/pages/registration-page/Model/questionnaire';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BloodDonorSearchNameSurname } from 'src/app/model/blood-donor/blood-donor-search-name-surname';

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css'],
})
export class AppointmentDetailsComponent implements OnInit {
  appointmentHistoryId: string = '-1';
  donorFullname: BloodDonorSearchNameSurname = { name: '', surname: '' };
  //donorId: string = '-1';

  private sub: any;

  questionaire: Questionnaire = {
    under50KG: false,
    feelsBad: false,
    skinChanges: false,
    highOrLowPresure: false,
    onTherapy: false,
    onMC: false,
    recentDentist: false,
    pricingOrSurgicalInterventionsOrBloodTransfusion: false,
  };

  canStartAppointment = true;
  checkedDidNotShowUp = false;
  checkedDoesNotQualify = false;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private questionaireService: QuestionnaireService,
    private appointmentService: AppointmentSchedulingHistoryService
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.appointmentHistoryId = params['appointmentHistoryId'];
      //this.donorId = params['donorId'];
      console.log(this.appointmentHistoryId);

      this.appointmentService
        .getDonorFullname(this.appointmentHistoryId)
        .subscribe((res) => {
          this.donorFullname.name = res.name;
          this.donorFullname.surname = res.surname;

          this.questionaireService
            .getByAshId(this.appointmentHistoryId)
            .subscribe((res1) => {
              this.questionaire = res1;
            });
        });
    });
  }

  clickedEventShowUp(event: any) {
    if (event.checked) {
      this.checkedDidNotShowUp = true;
      console.log('show checked');
    } else {
      this.checkedDidNotShowUp = false;
      console.log('show not checked');
    }

    this.setCanStartAppointment();
  }
  clickedEventQualify(event: any) {
    console.log(event);
    if (event.checked) {
      this.checkedDoesNotQualify = true;
      console.log('qualify checked');
    } else {
      this.checkedDoesNotQualify = false;
      console.log('qualify not checked');
    }

    this.setCanStartAppointment();
  }

  setCanStartAppointment() {
    if (this.checkedDidNotShowUp || this.checkedDoesNotQualify) {
      this.canStartAppointment = false;
      console.log('can start');
    } else {
      this.canStartAppointment = true;
      console.log('can not start');
    }
  }

  cancelAppointment() {
    let cancelation: AppointmentCancelation = {
      appointmentHistoryId: this.appointmentHistoryId, //TODO test, prvremeno
      //bloodDonorId: this.donorId,
      showedUp: this.checkedDidNotShowUp,
    };

    this.appointmentService
      .staffCancelAppointment(cancelation)
      .subscribe((res) => {
        console.log(res);
        this.router.navigate(['']);
      });
  }

  startAppointment() {
    console.log(this.appointmentHistoryId);
    this.router.navigate(['staff/create-report', this.appointmentHistoryId]);
  }
}
