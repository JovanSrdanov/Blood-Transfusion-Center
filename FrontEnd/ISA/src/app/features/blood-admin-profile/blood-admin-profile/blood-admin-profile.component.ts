import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-blood-admin-profile',
  templateUrl: './blood-admin-profile.component.html',
  styleUrls: ['./blood-admin-profile.component.css'],
})
export class BloodAdminProfileComponent implements OnInit {
  isChanging: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  enableChange() {
    this.isChanging = !this.isChanging;
  }
}
