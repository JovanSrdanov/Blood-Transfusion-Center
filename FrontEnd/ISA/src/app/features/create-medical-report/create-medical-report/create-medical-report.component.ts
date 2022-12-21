import { equipmentInfo } from './../../../model/equipment/equipment-info';
import { Component, OnInit } from '@angular/core';

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
  selectedEquipment: equipmentInfo = { id: '-1', name: '', quantity: -1 };
  quantity: number = -1;
  constructor() {}

  ngOnInit(): void {
    this.getEquipment();
  }

  getEquipment() {
    this.equipmentList.push({ id: '1', name: 'Oprema1', quantity: 5 });
    this.equipmentList.push({ id: '2', name: 'Oprema2', quantity: 6 });
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
  }
}
