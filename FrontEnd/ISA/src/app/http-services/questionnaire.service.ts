import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Questionnaire } from '../features/register-blood-donor/Model/questionnaire';

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireService {

  path: string = environment.backendPath + "/questionnaire";

  constructor(private readonly http: HttpClient) { }

  fillQuestionare = (dto: Questionnaire) => {
    return this.http.post(this.path + "/fillQuestionare", dto);
  }
}
