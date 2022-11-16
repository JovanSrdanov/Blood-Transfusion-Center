import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatTable } from '@angular/material/table';
import { BloodDonorService } from 'src/app/http-services/blood-donor.service';
import { BloodDonorInfo } from 'src/app/model/blood-donor/blood-donor-info';

@Component({
  selector: 'app-blood-donor-list',
  templateUrl: './blood-donor-list.component.html',
  styleUrls: ['./blood-donor-list.component.css']
})
export class BloodDonorListComponent implements OnInit {

  bloodDonorsSearched: BloodDonorInfo[] = [];
  displayedColumns: string[] = ["name", "surname", "email", "address", "jmbg", "phoneNumber", "institution", "gender"];

  _searchForm: FormGroup = new FormGroup({
    name : new FormControl<string>(''),
    surname : new FormControl<string>('')
  })

  get searchForm()
  {
    return this._searchForm.controls;
  }

  constructor(private readonly bloodDonorService: BloodDonorService) { }

  ngOnInit(): void {
    this.search();
  }
  @ViewChild(MatTable) table: MatTable<BloodDonorInfo> | null = null;
  search = () =>
  {
      this.bloodDonorService.searchByNameAndUsername({name:this.searchForm['name'].value, surname: this.searchForm['surname'].value})
      .subscribe((response:BloodDonorInfo[]) =>{
          this.bloodDonorsSearched = response;
          this.table?.renderRows();
      })
  }

  parseGender(gender:string)
  {
    if(gender === "MALE")
    {
      return "Male";
    }
    return "Female";
  }


}
