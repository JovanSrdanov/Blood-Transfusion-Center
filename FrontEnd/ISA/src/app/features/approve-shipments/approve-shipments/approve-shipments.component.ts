import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { merge, startWith, switchMap, catchError, map, of as observableOf, } from 'rxjs';
import { GPSDemandBloodShipmentService } from 'src/app/http-services/gpsdemand-blood-shipment.service';
import { PageDto } from 'src/app/model/PageDto';
import { BloodCenterService } from 'src/app/http-services/blood-center.service';


export interface PendingShipmentsDTO {
  id: string,
  serverPort: string,
  longitude: number,
  latitude: number,
  a_pos: number,
  a_neg: number,
  b_pos: number,
  b_neg: number,
  o_pos: number,
  o_neg: number,
  ab_pos: number,
  ab_neg: number,
}

export interface DialogData {
  PendingShipmentsDTO: PendingShipmentsDTO
}

@Component({
  selector: 'app-approve-shipments',
  templateUrl: './approve-shipments.component.html',
  styleUrls: ['./approve-shipments.component.css']
})
export class ApproveShipmentsComponent implements OnInit {

  helicopterStauts: string = "Please call the helicopter"
  dataSource: PendingShipmentsDTO[] = [];
  public tableDataSource: MatTableDataSource<PendingShipmentsDTO[]> =
    new MatTableDataSource<PendingShipmentsDTO[]>([]);
  displayedColumns: string[] = ['From', 'Longitude', "Latitude", "Send"];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSize: number = 3;
  pso: number[] = [this.pageSize];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;



  constructor(private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, public dialog: MatDialog, private router: Router, private bloodCenterService: BloodCenterService) { }

  ngOnInit(): void { }



  ngAfterViewInit() {
    merge(this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.gpsDemandBloodShipmentService!.getAllPendingShipments(
            this.pageSize,
            this.paginator.pageIndex,

          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<PendingShipmentsDTO[]> = {
              content: [],
              count: 0,
            };
            return empty;
          }

          this.resultsLength = data.count;
          return data;
        })
      )
      .subscribe((data) => (this.dataSource = data.content), err => {
        alert(err)
        this.router.navigate(["staff/staff-profile"]);
      });
  }

  openDialog(PendingShipmentsDTO: PendingShipmentsDTO) {
    this.dialog.open(SendingShipmentDialog, {
      data: {
        PendingShipmentsDTO: PendingShipmentsDTO,
      },
    });
  }
  callTheHelicopter(): void {
    this.bloodCenterService.callTheHelicopter().subscribe((res) => {
      console.log(res)
      this.helicopterStauts = "The helicopter is now here";
    }, (err) => {
      console.log(err)
      this.helicopterStauts = err.error;
    })
  }
}


@Component({
  selector: 'sending-shipment-dialog.html',
  templateUrl: 'sending-shipment-dialog.html',
})
export class SendingShipmentDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData, private gpsDemandBloodShipmentService: GPSDemandBloodShipmentService, private router: Router) { }

  secondsForm = new FormGroup({
    seconds: new FormControl<number>(1, [Validators.required]),
  });


  get form() {
    return this.secondsForm.controls;
  }


  sendShipment(PendingShipmentsDTO: PendingShipmentsDTO) {

    this.gpsDemandBloodShipmentService.sendShipment(PendingShipmentsDTO.id, this.form.seconds.value ?? 1).subscribe(res => {
      alert("Shipment sent!")
      this.router.navigate(["staff/staff-profile"]);
    }, err => {
      alert(err.error)
    })

  }


}
