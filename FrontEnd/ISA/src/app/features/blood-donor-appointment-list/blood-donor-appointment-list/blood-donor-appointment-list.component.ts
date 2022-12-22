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
    'end_time',
    'button',
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private appointmentsService: AppointmentSchedulingHistoryService
  ) {}

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
      id: '17547189-774D-4F75-8290-4D16BD39AFCD',
      issuingDate: '21/12/2022',
      startTime: '10:30',
      endTime: '11:30',
    });
    this.dataSource.push({
      id: '6D4AE778-0749-4309-B7BD-1850FFA7292E',
      issuingDate: '22/12/2022',
      startTime: '11:30',
      endTime: '12:30',
    });
    this.dataSource.push({
      id: '151743A8-B556-4B71-8B5B-382FB69ED05A',
      issuingDate: '23/12/2022',
      startTime: '12:30',
      endTime: '13:30',
    });
    console.log(this.dataSource);

    this.appointmentsService
      .bloodDonorAppointmentsForCenter(this.donorId)
      .subscribe((res) => {
        this.dataSource = res;
        console.log(this.dataSource);
      });

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
