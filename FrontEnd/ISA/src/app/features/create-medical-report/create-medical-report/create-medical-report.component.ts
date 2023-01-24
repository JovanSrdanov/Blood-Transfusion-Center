import { MatDialog } from '@angular/material/dialog';
import { bloodUpdate } from './../../../model/blood/blood-update';
import { bloodInfo } from './../../../model/blood/blood-info';
import { AppointmentReportService } from './../../../http-services/AppointmentReport/appointment-report.service';
import { BloodQuantityService } from './../../../http-services/blood-quantity.service';
import { EquipmentUpdate } from './../../../model/equipment/equipment-update';
import { equipmentInfo } from './../../../model/equipment/equipment-info';
import { Component, OnInit } from '@angular/core';
import { EquipmentService } from 'src/app/http-services/Equipment/equipment-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateAppointmentReport } from 'src/app/model/AppointmentReport/create-appointment';
import { NoteToDoctorModalComponent } from '../note-to-doctor-modal/note-to-doctor-modal.component';

@Component({
  selector: 'app-create-medical-report',
  templateUrl: './create-medical-report.component.html',
  styleUrls: ['./create-medical-report.component.css'],
})
export class CreateMedicalReportComponent implements OnInit {
  reportFinalText: string = '';

  bloodGroup: string = '';
  noteToDoctor: string = '';
  lungs: string = '';
  heart: string = '';
  bag: string = '';
  ta: string = '';
  tt: string = '';
  tb: string = '';
  appStart: string = '';
  appEnd: string = '';
  note: string = '';

  equipmentList: equipmentInfo[] = [];
  selectedEquipment: equipmentInfo = {
    equipmentId: '',
    name: '',
    quantity: 0,
  };

  bloodGroupList: bloodInfo[] = [];
  selectedBloodQuantity: bloodInfo = {
    bloodId: '',
    bloodGroup: 0,
    quantity: 0,
  };

  appointmentHistoryId: string = '-1';
  private sub: any;
  createBtnDisabled: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private equipmentService: EquipmentService,
    private appointmentReportService: AppointmentReportService,
    private bloodQuantityService: BloodQuantityService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.appointmentHistoryId = params['appointmentHistoryId'];
      console.log(this.appointmentHistoryId);
      this.getEquipmentAndBlood();
    });
  }

  getEquipmentAndBlood() {
    this.equipmentService.getEquipmentForCentre().subscribe((res) => {
      this.equipmentList = res;
      console.log(res);

      if (this.equipmentList.length > 0) {
        this.selectedEquipment = this.equipmentList[0];
      }

      this.bloodQuantityService
        .getBloodQuantityForCentre()
        .subscribe((res1) => {
          this.bloodGroupList = res1;
          console.log(res1);

          if (this.bloodGroupList.length > 0) {
            this.selectedBloodQuantity = this.bloodGroupList[0];
          }

          this.canCreateReport();
        });
    });
  }

  prepareText() {
    this.reportFinalText +=
      this.bloodGroup +
      '|' +
      this.noteToDoctor +
      '|' +
      this.lungs +
      '|' +
      this.heart +
      '|' +
      this.bag +
      '|' +
      this.ta +
      '|' +
      this.tt +
      '|' +
      this.tb +
      '|' +
      this.appStart +
      '|' +
      this.appEnd +
      '|' +
      this.note +
      '|';

    console.log(this.reportFinalText);
  }

  canCreateReport() {
    //console.log(event);
    console.log(this.selectedEquipment);
    console.log(this.selectedBloodQuantity);
    if (
      this.selectedEquipment.equipmentId == '' ||
      this.selectedBloodQuantity.bloodId == ''
    ) {
      console.log('nije selektovano jos');
      this.createBtnDisabled = true;
    } else {
      console.log('sad jeste');
      this.createBtnDisabled = false;
    }
  }

  openNoteToDoctorDialog() {
    const dialogRef = this.dialog.open(NoteToDoctorModalComponent, {
      data: { note: this.noteToDoctor },
      panelClass: 'my-css-class',
    });

    dialogRef.afterClosed().subscribe((res) => {
      this.noteToDoctor = res;
      console.log(this.noteToDoctor);
    });
  }

  openNoteDialog() {
    const dialogRef = this.dialog.open(NoteToDoctorModalComponent, {
      data: { note: this.note },
      panelClass: 'my-css-class',
    });

    dialogRef.afterClosed().subscribe((res) => {
      this.note = res;
      console.log(this.note);
    });
  }

  createReport() {
    this.prepareText();

    let equipmentUpdate: EquipmentUpdate = {
      equipmentId: this.selectedEquipment.equipmentId,
      quantity: this.selectedEquipment.quantity,
    };

    let bloodQuantityUpdate: bloodUpdate = {
      bloodId: this.selectedBloodQuantity.bloodId,
      quantity: this.selectedBloodQuantity.quantity,
    };

    let newReport: CreateAppointmentReport = {
      appointmentHistoryId: this.appointmentHistoryId,
      equipmentId: this.selectedEquipment.equipmentId,
      text: this.reportFinalText,
    };

    this.equipmentService
      .updateEquipmentInCentre(equipmentUpdate)
      .subscribe((res) => {
        console.log(res);

        this.bloodQuantityService
          .updateBloodQuantityInCentre(bloodQuantityUpdate)
          .subscribe((res1) => {
            console.log(res1);

            this.appointmentReportService
              .createAppointment(newReport)
              .subscribe((res2) => {
                console.log(res2);
                this.router.navigate(['']);
              });
          });
      });
  }
}
