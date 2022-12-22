import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { merge, startWith, switchMap, catchError, map, of as observableOf, } from 'rxjs';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';
import { PageDto } from 'src/app/model/PageDto';



export interface PremadeAppointmentDTO {
  appointmentId: string;
  dateTimeStart: Date
  duration: number;
}

@Component({
  selector: 'app-premade-appointments',
  templateUrl: './premade-appointments.component.html',
  styleUrls: ['./premade-appointments.component.css']
})
export class PremadeAppointmentsComponent implements OnInit {

  dataSource: PremadeAppointmentDTO[] = [{ appointmentId: "a", dateTimeStart: new Date(), duration: 40 }];
  public tableDataSource: MatTableDataSource<PremadeAppointmentDTO[]> =
    new MatTableDataSource<PremadeAppointmentDTO[]>([]);
  displayedColumns: string[] = ['Start date and time', 'Duration', "Schedule"];

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
    this.sub = this.route.params.subscribe((params) => {
      this.centerId = params['id']; // (+) converts string 'id' to a number
      console.log(this.centerId);
    });

  }


  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.appointmentService!.premadeAppointments(
            this.pageSize,
            this.paginator.pageIndex,
            this.sort.direction,
            this.sort.active,
            this.centerId
          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<PremadeAppointmentDTO[]> = {
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

  Schedule(appointmentId: string): void {
    console.log(appointmentId);
    this.appointmentService.schedule(appointmentId).subscribe(res => {
      alert("Appointment schedueled")
      this.router.navigate(['/blood-donor/my-appointments']);
    }, err => {
      alert(err.error.split("java.lang.Exception: ")[1]);
      console.log(err);
    })
  }

}
