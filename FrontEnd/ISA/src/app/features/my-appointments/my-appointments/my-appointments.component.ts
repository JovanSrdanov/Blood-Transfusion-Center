import { Component, OnInit } from '@angular/core';
import { AfterViewInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentService } from 'src/app/http-services/appointment.service';



import { MatSort } from '@angular/material/sort';
import {
  catchError,
  merge,
  startWith,
  switchMap,
  Observable,
  of as observableOf,
  map,
} from 'rxjs';
import { PageDto } from 'src/app/model/PageDto';




export interface BloodDonorAppointmentsDTO {
  appointmentId: string;
  center: string;
  date: Date;
  startTime: Date
  duration: number;
}

@Component({
  selector: 'app-my-appointments',
  templateUrl: './my-appointments.component.html',
  styleUrls: ['./my-appointments.component.css']
})
export class MyAppointmentsComponent implements AfterViewInit {
  dataSource: BloodDonorAppointmentsDTO[] = [];
  public tableDataSource: MatTableDataSource<BloodDonorAppointmentsDTO[]> =
    new MatTableDataSource<BloodDonorAppointmentsDTO[]>([]);
  displayedColumns: string[] = ['Center', 'Date', 'Start time', 'Duration', "Cancel"];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSize: number = 3;
  pso: number[] = [this.pageSize];
  @ViewChild(MatSort) sort!: MatSort;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  constructor(private appointmentService: AppointmentService) { }

  ngOnInit(): void {
  }
  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.appointmentService!.bloodDonorAppointments(
            this.pageSize,
            this.paginator.pageIndex,
            this.sort.direction,
            this.sort.active
          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<BloodDonorAppointmentsDTO[]> = {
              content: [],
              count: 0,
            };
            return empty;
          }

          // Only refresh the result length if there is new data. In case of rate
          // limit errors, we do not want to reset the paginator to zero, as that
          // would prevent users from re-triggering requests.
          this.resultsLength = data.count;
          return data;
        })
      )
      .subscribe((data) => (this.dataSource = data.content), err => { alert(err) });
  }

  setTableDataSource(data: any) {
    this.tableDataSource = new MatTableDataSource<BloodDonorAppointmentsDTO[]>(data);
    this.tableDataSource.paginator = this.paginator;
    this.tableDataSource.sort = this.sort;
  }


  cancelAppoitnment(id: string) {
    this.appointmentService.cancelAppointment(id).subscribe(res => {
      alert("Appointment canceled")
    }, err => {
      console.log(err)
    });

  }

}
