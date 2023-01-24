import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GPSDemandBloodShipmentService } from 'src/app/http-services/gpsdemand-blood-shipment.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-helicopter-map',
  templateUrl: './helicopter-map.component.html',
  styleUrls: ['./helicopter-map.component.css']
})
export class HelicopterMapComponent implements OnInit {
  private serverUrl = environment.backendPath + 'socket'

  constructor(private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, private router: Router) { }

  ngOnInit(): void {
    this.initializeWebSocketConnection();

    this.gpsDemandBloodShipmentService.canViewDelivery().subscribe(res => {
    }, err => {
      //alert(err.error)
      // this.router.navigate(["staff/staff-profile"]);
    })
  }

  initializeWebSocketConnection() {
    // serverUrl je vrednost koju smo definisali u registerStompEndpoints() metodi na serveru
  }

}
