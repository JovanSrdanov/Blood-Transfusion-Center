import { Component, OnInit } from '@angular/core';
import { StaffService } from 'src/app/http-services/staff.service';
import { BloodCenterService } from 'src/app/http-services/blood-center.service';
import { StaffBasicInfo } from 'src/app/model/staff/staff-basic-info';
import { BloodCenterBasicInfo } from 'src/app/model/blood-center/blood-center-basic-info';

@Component({
  selector: 'app-assign-blood-center',
  templateUrl: './assign-blood-center.component.html',
  styleUrls: ['./assign-blood-center.component.css']
})
export class AssignBloodCenterComponent implements OnInit {

  bloodCenters:BloodCenterBasicInfo[] = [];
  unemployedStaff: StaffBasicInfo[] = [];
  //Needed because select-list only knows how to do with string lists
  selectedCenterIndex: number = -1;
  selectedStaffIndex: number = -1;

  constructor(private readonly staffService: StaffService,
    private readonly bloodCenterService: BloodCenterService
    ) { }


  get centersNames()
  {
    let centerNames :string[] =[];
    this.bloodCenters.forEach(bc => centerNames.push(bc.name + ", " + bc.address.city + " " + bc.address.street + ' ' + bc.address.number))
    return centerNames;
  }
  get staffNames()
  {
    let staffsNames :string[] =[];
    this.unemployedStaff.forEach(staff => staffsNames.push(staff.name + ' ' + staff.surname + ', ' + staff.email) )
    return staffsNames;
  }

  get indexesInvalid():boolean
  {
    return !(this.selectedCenterIndex !== -1 && this.selectedStaffIndex !== -1);
  }

  ngOnInit(): void {
    this.bloodCenterService.getAllBloodCenters().subscribe((response:BloodCenterBasicInfo[]) =>{
      this.bloodCenters = response;
      console.table(response);
    })

    this.staffService.getUnemployedStaff().subscribe((response:StaffBasicInfo[]) => {
      this.unemployedStaff = response;
    })
  }

  selectCenter = (centerIndex:number) =>
  {
    this.selectedCenterIndex = centerIndex;
  }
  selectStaff = (staffIndex:number) =>
  {
    this.selectedStaffIndex = staffIndex;
  }

  assign = () =>
  {
    this.staffService.assignBloodCenter({
      staffId: this.unemployedStaff[this.selectedStaffIndex].id,
      bloodCenterId: this.bloodCenters[this.selectedCenterIndex].id
    }).subscribe(_ => {
      this.staffService.getUnemployedStaff().subscribe((response:StaffBasicInfo[]) => {
        this.unemployedStaff = response;
        this.selectedCenterIndex = -1;
        this.selectedStaffIndex = -1;
      })
    })
  }

}
