import { ProfileService } from './../services/profile.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-blood-admin-profile',
  templateUrl: './blood-admin-profile.component.html',
  styleUrls: ['./blood-admin-profile.component.css'],
})
export class BloodAdminProfileComponent implements OnInit {
  isPreventChangeAdmin: boolean = true;
  isPreventChangeCenter: boolean = true;

  staffInfo: any;
  staffInfoCopy: any;

  centerInfo: any;
  centerInfoCopy: any;

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
    this.staffInfo = this.profileService.getLoggedInStaffInfo();
    this.staffInfoCopy = structuredClone(this.staffInfo);

    this.centerInfo = this.profileService.getStaffCenterInfo();
    this.centerInfoCopy = structuredClone(this.centerInfo);
  }

  //ADMIN
  enableChangeAdmin(e: Event) {
    e.preventDefault();
    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
  }
  confirmChangeAdmin(event: Event) {
    event.preventDefault();
    this.profileService.updateStaffCenterInfo(this.staffInfo);
    this.staffInfoCopy = structuredClone(this.staffInfo);
    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
  }

  cancelChangeAdmin(event: Event) {
    event.preventDefault();
    this.staffInfo = structuredClone(this.staffInfoCopy);
    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
  }

  //CENTAR
  enableChangeCenter(event: Event) {
    event.preventDefault();
    this.isPreventChangeCenter = !this.isPreventChangeCenter;
  }

  confirmChangeCenter(event: Event) {
    event.preventDefault();
    this.centerInfoCopy = structuredClone(this.centerInfo);
    this.isPreventChangeCenter = !this.isPreventChangeCenter;
  }

  cancelChangeCenter(event: Event) {
    event.preventDefault();
    this.centerInfo = structuredClone(this.centerInfoCopy);
    this.isPreventChangeCenter = !this.isPreventChangeCenter;
  }
}
