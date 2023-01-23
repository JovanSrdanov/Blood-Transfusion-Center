import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.css'],
})
export class GoogleMapComponent {
  @Input() latitude: any;
  @Input() longitude: any;

  display: any;
  /*center: google.maps.LatLngLiteral = {
    lat: this.latitude,
    lng: this.longitude,
  };*/
  center: any;
  zoom = 4;

  /*markerPosition: google.maps.LatLngLiteral = {
    lat: this.latitude,
    lng: this.longitude,
  };*/
  markerPosition: any;
  markerOptions: google.maps.MarkerOptions = { draggable: false };

  constructor() {}

  ngOnInit(): void {
    console.log('google map lat long:');
    console.log(this.latitude + ' ' + this.longitude);

    this.center = {
      lat: this.latitude,
      lng: this.longitude,
    };

    this.markerPosition = {
      lat: this.latitude,
      lng: this.longitude,
    };
  }

  moveMap(event: google.maps.MapMouseEvent) {
    if (event.latLng != null) {
      //this.center = event.latLng.toJSON();
    }
  }

  move(event: google.maps.MapMouseEvent) {
    if (event.latLng != null) {
      this.display = event.latLng.toJSON();
    }
  }
}
