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
  isSucc1: boolean = false;
  isSucc2: boolean = false;

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
    const bldam = this.fb.group({
      A_POS: [],
      A_NEG: [],
      B_POS: [],
      B_NEG: [],
      O_POS: [],
      O_NEG: [],
      AB_POS: [],
      AB_NEG: [],
    });
    this.centerForm = this.fb.group({
      name: '',
      address: addr,
      bloodAmount: bldam,
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
        [Validators.required],
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
      bloodAmount: new FormGroup({
        A_POS: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[0].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        A_NEG: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[1].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        B_POS: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[2].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        B_NEG: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[3].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        O_POS: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[4].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        O_NEG: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[5].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        AB_POS: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[6].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
        AB_NEG: new FormControl(
          {
            value: this.centerInfo.bloodQuantities[7].quantity,
            disabled: true,
          },
          [Validators.required]
        ),
      }),
      description: [
        { value: this.centerInfo.description, disabled: true },
        [Validators.required],
      ],
      score: [{ value: 10, disabled: true }, [Validators.required]],
      appointments: [{ value: this.centerInfo.appointments, disabled: true }],
      staff: [
        {
          value: this.getAllStaffForAppointment(this.centerInfo.staff),
          disabled: true,
        },
      ],
    });
    this.centerForm.valueChanges.subscribe((currValue) => {
      this.centerInfo = currValue;
    });
  }

  getAllStaffForAppointment(staff: any): string {
    console.log(staff);
    let res = '';
    staff.forEach((value: any) => {
      if (value != undefined) {
        res += value.id;
        res += ' | ';
      } else {
        res += value;
        res += ' | ';
      }
    });
    return res;
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
    this.staffInfo.bloodCenter = this.centerInfo;

    this.profileService
      .updateStaffInfo(this.staffId, this.staffInfo)
      .subscribe((res) => {
        console.log(res);
        //alert('Profile successfuly changed');
        this.isSucc1 = true;
        setTimeout(() => {
          this.isSucc1 = false;
        }, 2500);
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
    this.centerForm.get('bloodAmount')?.get('A_POS')?.disable();
    this.centerForm.get('bloodAmount')?.get('A_NEG')?.disable();
    this.centerForm.get('bloodAmount')?.get('B_POS')?.disable();
    this.centerForm.get('bloodAmount')?.get('B_NEG')?.disable();
    this.centerForm.get('bloodAmount')?.get('O_POS')?.disable();
    this.centerForm.get('bloodAmount')?.get('O_NEG')?.disable();
    this.centerForm.get('bloodAmount')?.get('AB_POS')?.disable();
    this.centerForm.get('bloodAmount')?.get('AB_NEG')?.disable();
  }

  confirmChangeCenter(event: Event) {
    event.preventDefault();

    this.profileService
      .updateStaffCenterInfo(this.centerId, this.centerInfo)
      .subscribe((res) => {
        console.log(res);
        //alert('Blood center profile successfuly changed');
        this.isSucc2 = true;
        setTimeout(() => {
          this.isSucc2 = false;
        }, 2500);
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
