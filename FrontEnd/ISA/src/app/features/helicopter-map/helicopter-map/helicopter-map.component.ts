import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GPSDemandBloodShipmentService } from 'src/app/http-services/gpsdemand-blood-shipment.service';
import { environment } from 'src/environments/environment';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';


import Map from 'ol/Map';
import View from 'ol/View';
import { OSM, Vector } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import { fromLonLat } from 'ol/proj';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { Icon, Style } from 'ol/style';
import { Point } from 'ol/geom';
import Feature from 'ol/Feature';
import Group from 'ol/layer/Group';

export interface LATLON {
  lat: number
  lng: number;
}

@Component({
  selector: 'app-helicopter-map',
  templateUrl: './helicopter-map.component.html',
  styleUrls: ['./helicopter-map.component.css']
})
export class HelicopterMapComponent implements OnInit {
  private serverUrl = environment.backendPath + '/socket'
  private stompClient: any;

  markers: LATLON[] = [];
  zoom = 7
  center = {
    lat: 0,
    lng: 0,
  }
  srcPos = {
    lat: 0,
    lng: 0,
  }
  desPos = {
    lat: 0,
    lng: 0,
  }
  curPos = {
    lat: 0,
    lng: 0,
  }

  srcPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: true,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_blue.png'
  };
  desPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: true,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_green.png'
  };
  curPosMarkerOptions: google.maps.MarkerOptions = {
    draggable: true,
    icon: 'http://labs.google.com/ridefinder/images/mm_20_red.png'
  };

  isLoaded: boolean = false;
  isCustomSocketOpened = false;

  vectorLayerC: any;

  // nove

  public map!: Map
  constructor(private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, private router: Router) {
  }

  ngOnInit(): void {
    this.initializeWebSocketConnection();
    this.gpsDemandBloodShipmentService.canViewDelivery().subscribe(res => {
    }, err => {
      alert(err.error)
      this.router.navigate(["staff/staff-profile"]);
    })


    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      target: 'map',
      view: new View({
        center: fromLonLat([21, 44]),
        zoom: 7, maxZoom: 18,
      }),
    });



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
      this.stompClient.subscribe("/socket-publisher", (message: any) => {
        this.handleResult(message);
      });
    }
  }

  handleResult(message: any) {
    if (message.body == "Arrived") {
      alert("The hellicopter has arrived")
      this.router.navigate(["staff/staff-profile"]);
    }
    if (message.body) {
      let HelicopterPosition = JSON.parse(message.body)
      this.curPos.lat = HelicopterPosition.curLatitude;
      this.curPos.lng = HelicopterPosition.curLongitude;

      this.srcPos.lat = HelicopterPosition.srcLatitude;
      this.srcPos.lng = HelicopterPosition.srcLongitude;

      this.desPos.lat = HelicopterPosition.destLatitude;
      this.desPos.lng = HelicopterPosition.destLongitude;


      this.map.removeLayer(this.vectorLayerC)

      const markerC = new Feature({
        geometry: new Point(fromLonLat([this.curPos.lng, this.curPos.lat]))
      });
      markerC.setStyle(new Style({
        image: new Icon({

          scale: 0.15,
          src: "https://cdn-icons-png.flaticon.com/512/5225/5225195.png",
        }),
      }));
      this.vectorLayerC = new VectorLayer({
        source: new VectorSource({
          features: [markerC]
        })
      });
      this.map.addLayer(this.vectorLayerC)


      const markerS = new Feature({
        geometry: new Point(fromLonLat([this.srcPos.lng, this.srcPos.lat]))
      });
      markerS.setStyle(new Style({
        image: new Icon({
          scale: 0.1,
          src: "https://cdn-icons-png.flaticon.com/512/205/205916.png",
        }),
      }));

      const vectorLayerS = new VectorLayer({
        source: new VectorSource({
          features: [markerS]
        })
      });
      this.map.addLayer(vectorLayerS)

      const markerD = new Feature({
        geometry: new Point(fromLonLat([this.desPos.lng, this.desPos.lat]))
      });

      markerD.setStyle(new Style({
        image: new Icon({
          scale: 0.1,
          src: "https://cdn-icons-png.flaticon.com/512/3309/3309748.png",
        }),
      }));
      const vectorLayerD = new VectorLayer({
        source: new VectorSource({
          features: [markerD]
        })
      });
      this.map.addLayer(vectorLayerD)

    }
  }


}
