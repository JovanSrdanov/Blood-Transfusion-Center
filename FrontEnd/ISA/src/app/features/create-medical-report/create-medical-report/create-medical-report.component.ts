import { AppointmentReportService } from './../../../http-services/AppointmentReport/appointment-report.service';
import { EquipmentUpdate } from './../../../model/equipment/equipment-update';
import { equipmentInfo } from './../../../model/equipment/equipment-info';
import { Component, OnInit } from '@angular/core';
import { EquipmentService } from 'src/app/http-services/Equipment/equipment-service.service';
import { ActivatedRoute } from '@angular/router';
import { CreateAppointmentReport } from 'src/app/model/AppointmentReport/create-appointment';

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
    equipmentId: 'DB69491D-C096-4420-8AF5-FC28BCA1BA23',
    name: '',
    quantity: -1,
  };
  quantity: number = -1;

  appointmentHistoryId: string = '-1';
  private sub: any;

  constructor(
    private route: ActivatedRoute,
    private equipmentService: EquipmentService,
    private appointmentReportService: AppointmentReportService
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.appointmentHistoryId = params['appointmentHistoryId'];
      console.log(this.appointmentHistoryId);
      this.getEquipment();
    });
  }

  getEquipment() {
    this.equipmentList.push({ equipmentId: '1', name: 'Oprema1', quantity: 5 });
    this.equipmentList.push({ equipmentId: '2', name: 'Oprema2', quantity: 6 });

    this.equipmentService.getEquipmentForCentre().subscribe((res) => {
      this.equipmentList = res;
      console.log(res);
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

  createReport() {
    this.prepareText();

    let equipmentUpdate: EquipmentUpdate = {
      equipmentId: this.selectedEquipment.equipmentId,
      quantity: this.selectedEquipment.quantity,
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
      });

    this.appointmentReportService
      .createAppointment(newReport)
      .subscribe((res) => {
        console.log(res);
      });
  }
}
