import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Questionnaire } from '../pages/registration-page/Model/questionnaire';

@Injectable({
  providedIn: 'root',
})
export class QuestionnaireService {
  path: string = environment.backendPath + '/questionnaire';

  constructor(private readonly http: HttpClient) {}

  fillQuestionare = (dto: Questionnaire) => {
    return this.http.post(this.path + '/fillQuestionare', dto);
  };

  getByAshId(ashId: string): Observable<Questionnaire> {
    return this.http.get<Questionnaire>(
      this.path + '/getQuestionaire/' + ashId
    );
  }
}
