import { ProfileService } from './../../../http-services/staff/profile.service';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';

@Component({
  selector: 'app-blood-admin-profile',
  templateUrl: './staff-profile.component.html',
  styleUrls: ['./staff-profile.component.css'],
})
export class StaffProfileComponent implements OnInit {
  staffForm: FormGroup;
  centerForm: FormGroup;

  isPreventChangeStaff: boolean = true;
  isPreventChangeCenter: boolean = true;
  isPreventChangePassword: boolean = true;

  staffInfo: any;
  staffId: any;
  staffEmail: any;
  staffAddress: any;
  staffInfoCopy: any;

  centerInfo: any;
  centerId: any;
  centerInfoCopy: any;

  constructor(private profileService: ProfileService, private fb: FormBuilder) {
    this.staffForm = this.fb.group({
      name: '',
      surname: '',
      email: '',
      phoneNumber: '',
    });
    const addr = this.fb.group({
      street: [],
      number: [],
      city: [],
      country: [],
      latitude: [],
      longitude: [],
    });
    this.centerForm = this.fb.group({
      name: '',
      address: addr,
      description: '',
      score: 0,
      appointments: [],
      staff: [],
    });
  }

  ngOnInit(): void {
    this.profileService.getLoggedInStaffInfo().subscribe((res) => {
      console.log(res);
      this.staffInfo = res;
      this.staffId = this.staffInfo.id;
      this.staffEmail = this.staffInfo.email;
      this.staffAddress = this.staffInfo.address;
      this.staffInfoCopy = structuredClone(this.staffInfo);
      this.createFormStaff();

      if (this.staffInfo.bloodCenter != null) {
        this.centerInfo = this.staffInfo.bloodCenter;
        this.centerId = this.staffInfo.bloodCenter.id;
        console.log(this.centerId);
      }
      this.centerInfoCopy = structuredClone(this.centerInfo);
      this.createFormCenter();
    });
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

      phoneNumber: [
        { value: this.staffInfo.phoneNumber, disabled: true },
        [Validators.required, Validators.pattern('^[0-9]{9}$')],
      ],
      email: [
        { value: this.staffInfo.email, disabled: true },
        [Validators.required, Validators.email],
      ],
    });
    this.staffForm.valueChanges.subscribe((currValue) => {
      this.staffInfo = currValue;
    });
  }

  createFormCenter(): void {
    this.centerForm = this.fb.group({
      name: [
        { value: this.centerInfo.name, disabled: true },
        [Validators.required],
      ],
      //address: [{ value: addr, disabled: true }, [Validators.required]],
      address: new FormGroup({
        street: new FormControl(
          { value: this.centerInfo.address.street, disabled: true },
          [Validators.required]
        ),
        number: new FormControl(
          { value: this.centerInfo.address.number, disabled: true },
          [Validators.required, Validators.pattern('^[0-9]+(/?[0-9]+)?$')]
        ),
        city: new FormControl(
          { value: this.centerInfo.address.city, disabled: true },
          [Validators.required]
        ),
        country: new FormControl(
          { value: this.centerInfo.address.country, disabled: true },
          [Validators.required]
        ),
        latitude: new FormControl(
          { value: this.centerInfo.address.latitude, disabled: true },
          [Validators.required, Validators.pattern('^[+-]?[0-9]+.?[0-9]*$')]
        ),
        longitude: new FormControl(
          {
            value: this.centerInfo.address.longitude,
            disabled: true,
          },
          [Validators.required, Validators.pattern('^[+-]?[0-9]+.?[0-9]*$')]
        ),
      }),
      description: [
        { value: this.centerInfo.description, disabled: true },
        [Validators.required],
      ],
      score: [{ value: 10, disabled: true }, [Validators.required]],
      appointments: [{ value: [], disabled: true }],
      staff: [{ value: [], disabled: true }],
    });
    this.centerForm.valueChanges.subscribe((currValue) => {
      this.centerInfo = currValue;
    });
  }

  //ADMIN
  enableChangeStaff(e: Event) {
    e.preventDefault();
    console.log(this.staffId);
    this.isPreventChangeStaff = !this.isPreventChangeStaff;
    this.staffForm.enable();
    console.log(this.staffInfo);

    this.staffForm.get('email')?.disable();
  }
  confirmChangeStaff(event: Event) {
    event.preventDefault();
    this.staffInfo.id = this.staffId;
    this.staffInfo.email = this.staffEmail;
    this.staffInfo.address = this.staffAddress;

    this.profileService
      .updateStaffInfo(this.staffId, this.staffInfo)
      .subscribe((res) => {
        console.log(res);
      });
    this.staffInfoCopy = structuredClone(this.staffInfo);

    this.isPreventChangeStaff = !this.isPreventChangeStaff;
    this.staffForm.disable();
  }

  cancelChangeStaff(event: Event) {
    event.preventDefault();

    this.staffInfo = structuredClone(this.staffInfoCopy);
    this.staffForm.patchValue({
      name: this.staffInfo.name,
      surname: this.staffInfo.surname,

      phoneNumber: this.staffInfo.phoneNumber,
    });

    this.isPreventChangeStaff = !this.isPreventChangeStaff;
    this.staffForm.disable();
  }

  //CENTAR
  enableChangeCenter(event: Event) {
    event.preventDefault();

    this.isPreventChangeCenter = !this.isPreventChangeCenter;
    this.centerForm.enable();

    this.centerForm.get('score')?.disable();
    this.centerForm.get('appointments')?.disable();
    this.centerForm.get('staff')?.disable();
  }

  confirmChangeCenter(event: Event) {
    event.preventDefault();

    this.profileService
      .updateStaffCenterInfo(this.centerId, this.centerInfo)
      .subscribe((res) => {
        console.log(res);
      });

    this.centerInfoCopy = structuredClone(this.centerInfo);

    this.isPreventChangeCenter = !this.isPreventChangeCenter;
    this.centerForm.disable();
  }

  cancelChangeCenter(event: Event) {
    event.preventDefault();

    this.centerInfo = structuredClone(this.centerInfoCopy);
    this.centerForm.patchValue({
      name: this.centerInfo.name,
      address: {
        street: this.centerInfo.address.street,
        number: this.centerInfo.address.number,
        city: this.centerInfo.address.city,
        country: this.centerInfo.address.country,
        latitude: this.centerInfo.address.latitude,
        longitude: this.centerInfo.address.longitude,
      },
      description: this.centerInfo.description,
      score: this.centerInfo.score,
      appointments: this.centerInfo.appointments,
      staff: this.centerInfo.staff,
    });

    this.isPreventChangeCenter = !this.isPreventChangeCenter;
    this.centerForm.disable();
  }

  enableChangePassword(event: Event) {
    this.isPreventChangePassword = !this.isPreventChangePassword;
  }
}
