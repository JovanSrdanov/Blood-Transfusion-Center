import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GPSDemandBloodShipmentService } from 'src/app/http-services/gpsdemand-blood-shipment.service';

@Component({
  selector: 'app-helicopter-map',
  templateUrl: './helicopter-map.component.html',
  styleUrls: ['./helicopter-map.component.css']
})
export class HelicopterMapComponent implements OnInit {

  constructor(private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, private router: Router) { }

  ngOnInit(): void {

    this.gpsDemandBloodShipmentService.canViewDelivery().subscribe(res => {
      console.log(res)
    }, err => {
      alert(err.error)
      this.router.navigate(["staff/staff-profile"]);
    })

  }

}
