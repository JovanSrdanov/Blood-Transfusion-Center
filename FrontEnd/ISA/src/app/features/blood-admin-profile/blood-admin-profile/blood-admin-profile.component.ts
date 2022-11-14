import { ProfileService } from './../../../http-services/staff/profile.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-blood-admin-profile',
  templateUrl: './blood-admin-profile.component.html',
  styleUrls: ['./blood-admin-profile.component.css'],
})
export class BloodAdminProfileComponent implements OnInit {
  //FORMA
  staffForm: FormGroup;
  //FORMA

  isPreventChangeAdmin: boolean = true;
  isPreventChangeCenter: boolean = true;

  staffInfo: any;
  staffId: any;
  staffInfoCopy: any;

  centerInfo: any;
  centerInfoCopy: any;

  constructor(private profileService: ProfileService, private fb: FormBuilder) {
    this.staffForm = this.fb.group({
      name: '',
      surname: '',
      email: '',
      phoneNumber: '',
    });
    this.staffForm.valueChanges.subscribe();
  }

  ngOnInit(): void {
    this.profileService.getLoggedInStaffInfo().subscribe((res) => {
      console.log(res);
      this.staffInfo = res;
      this.staffId = this.staffInfo.id;
      this.staffInfoCopy = structuredClone(this.staffInfo);
      this.createFormStaff();
    });

    this.centerInfo = this.profileService.getStaffCenterInfo();
    this.centerInfoCopy = structuredClone(this.centerInfo);
  }

  createFormStaff(): void {
    this.staffForm = this.fb.group({
      name: [
        { value: this.staffInfo.name, disabled: true },
        [Validators.required],
      ],
      surname: [
        { value: this.staffInfo.surname, disabled: true },
        [Validators.required],
      ],
      email: [
        { value: 'test@testmail.com', disabled: true },
        [Validators.required, Validators.email],
      ],
      phoneNumber: [
        { value: this.staffInfo.phoneNumber, disabled: true },
        [Validators.required],
      ],
    });
    this.staffForm.valueChanges.subscribe((currValue) => {
      this.staffInfo = currValue;
    });
  }

  //ADMIN
  enableChangeAdmin(e: Event) {
    e.preventDefault();

    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
    this.staffForm.enable();
  }
  confirmChangeAdmin(event: Event) {
    event.preventDefault();

    this.profileService
      .updateStaffInfo(this.staffId, this.staffInfo)
      .subscribe((res) => {
        console.log(res);
      });
    this.staffInfoCopy = structuredClone(this.staffInfo);

    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
    this.staffForm.disable();
  }

  cancelChangeAdmin(event: Event) {
    event.preventDefault();

    this.staffInfo = structuredClone(this.staffInfoCopy);
    this.staffForm.patchValue({
      name: this.staffInfo.name,
      surname: this.staffInfo.surname,
      email: this.staffInfo.email,
      phoneNumber: this.staffInfo.phoneNumber,
    });

    this.isPreventChangeAdmin = !this.isPreventChangeAdmin;
    this.staffForm.disable();
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
