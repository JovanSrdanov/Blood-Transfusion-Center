
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { merge, startWith, switchMap, catchError, map, of as observableOf, } from 'rxjs';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
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

  centerId: string = '-1';
  private sub: any;
  constructor(private appointmentService: AppointmentServiceService, private route: ActivatedRoute, private router: Router) { }
  ngOnInit(): void {
  }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
  }

}
