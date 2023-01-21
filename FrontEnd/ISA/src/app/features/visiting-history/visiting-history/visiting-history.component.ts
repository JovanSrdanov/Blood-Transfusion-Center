
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { merge, startWith, switchMap, catchError, map, of as observableOf, } from 'rxjs';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { AppointmentReportService } from 'src/app/http-services/AppointmentReport/appointment-report.service';
import { PageDto } from 'src/app/model/PageDto';

export interface AppointmentReportDTO {
  appointmentReportId: string;
  dateTimeStart: Date
  duration: number;
  text: string;
}

@Component({
  selector: 'app-visiting-history',
  templateUrl: './visiting-history.component.html',
  styleUrls: ['./visiting-history.component.css']
})
export class VisitingHistoryComponent implements OnInit {

  dataSource: AppointmentReportDTO[] = [];
  public tableDataSource: MatTableDataSource<AppointmentReportDTO[]> =
    new MatTableDataSource<AppointmentReportDTO[]>([]);
  displayedColumns: string[] = ['Start date and time', 'Duration', "Information"];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSize: number = 3;
  pso: number[] = [this.pageSize];
  @ViewChild(MatSort) sort!: MatSort;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  constructor(private appointmentReportService: AppointmentReportService) { }
  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.appointmentReportService!.reportHistory(
            this.pageSize,
            this.paginator.pageIndex,
            this.sort.direction,
            this.sort.active,

          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<AppointmentReportDTO[]> = {
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
}


