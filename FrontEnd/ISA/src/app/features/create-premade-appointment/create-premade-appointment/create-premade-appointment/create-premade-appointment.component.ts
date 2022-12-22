import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-premade-appointment',
  templateUrl: './create-premade-appointment.component.html',
  styleUrls: ['./create-premade-appointment.component.css']
})
export class CreatePremadeAppointmentComponent implements OnInit {

  dateFormCtrl = new FormControl(new Date());

  constructor(private readonly router: Router,) { }
  ngOnInit(): void {

  }

  generate(): void {
    console.table("aaa " + this.dateFormCtrl.value)
  }

}
