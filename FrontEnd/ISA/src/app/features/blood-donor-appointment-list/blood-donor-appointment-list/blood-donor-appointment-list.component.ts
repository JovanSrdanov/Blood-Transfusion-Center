import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { appointmentBloodDonorInfo } from 'src/app/model/appointment/appointment-blood-donor-info';

@Component({
  selector: 'app-blood-donor-appointment-list',
  templateUrl: './blood-donor-appointment-list.component.html',
  styleUrls: ['./blood-donor-appointment-list.component.css'],
})
export class BloodDonorAppointmentListComponent implements OnInit {
  donorId: number = -1;
  private sub: any;

  dataSource: appointmentBloodDonorInfo[] = [];
  displayedColumns: string[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe((params) => {
      this.donorId = +params['id']; // (+) converts string 'id' to a number
      console.log(this.donorId);

      this.dataSource.push({ time: '10:30', duration: 30 });
      this.dataSource.push({ time: '11:30', duration: 30 });
      console.log(this.dataSource);
    });
  }

  getAppointment(row: any) {
    console.log(row);
  }

  viewDetails() {}

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
