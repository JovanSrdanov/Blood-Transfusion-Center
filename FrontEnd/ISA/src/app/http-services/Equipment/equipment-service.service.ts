import { EquipmentUpdate } from './../../model/equipment/equipment-update';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { equipmentInfo } from 'src/app/model/equipment/equipment-info';

@Injectable({
  providedIn: 'root',
})
export class EquipmentService {
  path: string = environment.backendPath + '/equipment';

  constructor(private readonly http: HttpClient) {}

  getEquipmentForCentre(): Observable<equipmentInfo[]> {
    return this.http.get<equipmentInfo[]>(this.path + '/get-by-centre');
  }

  updateEquipmentInCentre(equipmentUpdate: EquipmentUpdate): Observable<any> {
    return this.http.put<any>(this.path, equipmentUpdate);
  }
}
