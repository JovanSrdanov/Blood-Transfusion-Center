import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GPSDemandBloodShipmentService } from 'src/app/http-services/gpsdemand-blood-shipment.service';
import { environment } from 'src/environments/environment';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';



@Component({
  selector: 'app-helicopter-map',
  templateUrl: './helicopter-map.component.html',
  styleUrls: ['./helicopter-map.component.css']
})
export class HelicopterMapComponent implements OnInit {
  private serverUrl = environment.backendPath + '/socket'
  private stompClient: any;

  zoom = 7
  center = {
    lat: 44.0165,
    lng: 21.0059,
  };

  srcPos = {
    lat: 45.3834,
    lng: 20.3906
  }

  desPos = {
    lat: 44.3834,
    lng: 20.3906
  }

  curPos = {
    lat: 43.3834,
    lng: 20.3906
  }


  srcPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: false,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_blue.png'
  };
  desPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: false,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_green.png'
  };
  curPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: false,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_red.png'
  };

  isLoaded: boolean = false;
  isCustomSocketOpened = false;


  constructor(private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, private router: Router) {



  }

  ngOnInit(): void {
    /*     this.gpsDemandBloodShipmentService.canViewDelivery().subscribe(res => {
        }, err => {
          //alert(err.error)
          // this.router.navigate(["staff/staff-profile"]);
        }) */
    this.initializeWebSocketConnection();



  }

  initializeWebSocketConnection() {
    // serverUrl je vrednost koju smo definisali u registerStompEndpoints() metodi na serveru
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.isLoaded = true;
      that.openGlobalSocket()
    });

  }
  openGlobalSocket() {
    if (this.isLoaded) {
      this.stompClient.subscribe("/socket-publisher", (message: { body: string; }) => {
        this.handleResult(message);
      });
    }
  }

  handleResult(message: { body: string; }) {
    if (message.body) {
      let messageResult: any = JSON.parse(message.body);
      console.table(message);

    }
  }


}
