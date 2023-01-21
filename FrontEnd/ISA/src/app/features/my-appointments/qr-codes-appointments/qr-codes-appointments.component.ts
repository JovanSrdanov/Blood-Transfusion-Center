import { Component, EventEmitter, Inject, Output, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppointmentSchedulingHistoryService } from 'src/app/http-services/appointment-scheduling-history.service';
import { QrCodeASHDTO } from './../../../http-services/appointment-scheduling-history.service';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { merge, startWith, switchMap, catchError, map, of as observableOf, } from 'rxjs';
import { PageDto } from 'src/app/model/PageDto';
import { MatPaginator } from '@angular/material/paginator';

export interface DialogData {
  qrcode: QrCodeASHDTO
}
@Component({
  selector: 'app-qr-codes-appointments',
  templateUrl: './qr-codes-appointments.component.html',
  styleUrls: ['./qr-codes-appointments.component.css']
})
export class QRCodesAppointmentsComponent {

  dataSource: QrCodeASHDTO[] = [];
  public tableDataSource: MatTableDataSource<QrCodeASHDTO[]> =
    new MatTableDataSource<QrCodeASHDTO[]>([]);
  displayedColumns: string[] = ['Issuing date', 'Status', "View QR Code"];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSize: number = 4;
  pso: number[] = [this.pageSize];
  @ViewChild(MatSort) sort!: MatSort;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  filters: string[] = ["PENDING", "PROCESSED", "REJECTED", "CANCELED"];
  filterBy = this.filters[0];
  @Output() filterEvent = new EventEmitter();

  constructor(private appointmentSchedulingHistory: AppointmentSchedulingHistoryService, public dialog: MatDialog) { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page, this.filterEvent)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.appointmentSchedulingHistory!.QRbloodDonorAppointments(
            this.pageSize,
            this.paginator.pageIndex,
            this.sort.direction,
            this.filterBy,

          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<QrCodeASHDTO[]> = {
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

  openDialog(QrCodeASHDTO: QrCodeASHDTO) {
    this.dialog.open(QRCodeDialog, {
      data: {
        qrcode: QrCodeASHDTO,
      },
    });
  }

  doFilter(): void {
    this.filterEvent.emit();
  }

}
@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: 'QR-code-dialog.html',
})
export class QRCodeDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) { }
}
