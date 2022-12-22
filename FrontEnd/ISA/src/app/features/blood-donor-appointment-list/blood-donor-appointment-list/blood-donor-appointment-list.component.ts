import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentSchedulingHistoryService } from 'src/app/http-services/appointment-scheduling-history.service';
import { appointmentBloodDonorInfo } from 'src/app/model/appointment/appointment-blood-donor-info';

@Component({
  selector: 'app-blood-donor-appointment-list',
  templateUrl: './blood-donor-appointment-list.component.html',
  styleUrls: ['./blood-donor-appointment-list.component.css'],
})
export class BloodDonorAppointmentListComponent implements OnInit {
  donorId: string = '-1';
  private sub: any;

  dataSource: appointmentBloodDonorInfo[] = [];
  displayedColumns: string[] = [
    'issuing_date',
    'start_time',
    'duration',
    'button',
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private appointmentsService: AppointmentSchedulingHistoryService
  ) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.donorId = params['id']; // (+) converts string 'id' to a number
      console.log(this.donorId);
    });
    this.FillTable();
  }

  @ViewChild(MatTable) table: MatTable<appointmentBloodDonorInfo> | null = null;
  FillTable = () => {
    this.dataSource.push({
      id: '1',
      issuingDate: '21/12/2022',
      time: '10:30',
      duration: 30,
    });
    this.dataSource.push({
      id: '2',
      issuingDate: '22/12/2022',
      time: '11:30',
      duration: 30,
    });
    this.dataSource.push({
      id: '3',
      issuingDate: '23/12/2022',
      time: '12:30',
      duration: 30,
    });
    console.log(this.dataSource);

    // this.appointmentsService
    //   .bloodDonorAppointmentsForCenter(this.donorId)
    //   .subscribe((res) => {
    //     this.dataSource = res;
    //   });

    this.table?.renderRows();
  };

  getAppointment(row: any) {
    console.log(row);
  }

  viewDetails(event: any, id: string) {
    console.log(id);
    this.router.navigate(['staff/appointment-details', id, this.donorId]);
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
