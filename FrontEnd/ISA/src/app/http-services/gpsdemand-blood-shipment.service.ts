import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PendingShipmentsDTO } from '../features/approve-shipments/approve-shipments/approve-shipments.component';
import { PageDto } from '../model/PageDto';

@Injectable({
  providedIn: 'root'
})
export class GPSDemandBloodShipmentService {

  path: string = environment.backendPath + '/gps-demand-blood-shipment';

  constructor(private readonly http: HttpClient) { }

  getAllPendingShipments(pageSize: number,
    page: number): Observable<PageDto<PendingShipmentsDTO[]>> {
    return this.http.get<PageDto<PendingShipmentsDTO[]>>(this.path + '/get-all-pending-shipments?page='
      + page + "&pageSize=" + pageSize);
  }


}
