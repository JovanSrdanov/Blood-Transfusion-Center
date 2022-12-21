import { Component, OnInit } from '@angular/core';
import { AfterViewInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
export interface Appointment {
  appointmentId: string;
  center: string;
  date: Date;
  startTime: Date
  duration: number;
}

const ELEMENT_DATA: Appointment[] = [
  { appointmentId: "1", center: "Centar neki", date: new Date, startTime: new Date, duration: 34 },
  { appointmentId: "2", center: "Centar neki", date: new Date, startTime: new Date, duration: 30 },


];
@Component({
  selector: 'app-my-appointments',
  templateUrl: './my-appointments.component.html',
  styleUrls: ['./my-appointments.component.css']
})
export class MyAppointmentsComponent implements AfterViewInit {
  displayedColumns: string[] = ['Center', 'Date', 'Start time', 'Duration', "Cancel"];
  dataSource = new MatTableDataSource<Appointment>(ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor() { }

  ngOnInit(): void {

  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  cancelAppoitnment(id: string) {

    // Ovako se brise kad prodje uspesan cancel
    // Id bude smao string
    this.dataSource.data = this.dataSource.data.filter((u) => u.appointmentId !== id);;

  }

}
