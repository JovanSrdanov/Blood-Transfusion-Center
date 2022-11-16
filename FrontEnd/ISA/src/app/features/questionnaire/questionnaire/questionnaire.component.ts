import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Questionnaire } from '../../register-blood-donor/Model/questionnaire';
import { QuestionnaireService } from './../../../http-services/questionnaire.service';

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css']
})
export class QuestionnaireComponent implements OnInit {
  checkedUnder50KG = false;
  checkedFeelsBad = false;
  checkedSkinChanges = false;
  checkedHighOrLowPresure = false;
  checkedOnTherapy = false;
  checkedOnMC = false;
  checkedRecentDentist = false;
  checkedPricingOrSurgicalInterventionsOrBloodTransfusion = false;




  constructor(private readonly questionnaireService: QuestionnaireService, private readonly router: Router) { }

  ngOnInit(): void {
  }
  submit(): void {
    let questionnaire: Questionnaire = {
      under50KG: this.checkedUnder50KG,
      feelsBad: this.checkedFeelsBad,
      skinChanges: this.checkedSkinChanges,
      highOrLowPresure: this.checkedHighOrLowPresure,
      onTherapy: this.checkedOnTherapy,
      onMC: this.checkedOnMC,
      recentDentist: this.checkedRecentDentist,
      pricingOrSurgicalInterventionsOrBloodTransfusion: this.checkedPricingOrSurgicalInterventionsOrBloodTransfusion,
    }

    this.questionnaireService.fillQuestionare(questionnaire).subscribe(res => {
      console.log(res);
      this.router.navigate(['']);
    })

  }
}
