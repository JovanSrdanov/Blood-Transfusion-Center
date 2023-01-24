import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, SortDirection } from '@angular/material/sort';
import { Router } from '@angular/router';
import { catchError, map, merge, startWith, switchMap } from 'rxjs';
import { BloodDonorService } from 'src/app/http-services/blood-donor.service';
import { BloodDonorInfo } from 'src/app/model/blood-donor/blood-donor-info';
import { PageDto } from 'src/app/model/PageDto';
import { SortType } from 'src/app/model/sort-type';

@Component({
  selector: 'app-blood-donor-list',
  templateUrl: './blood-donor-list.component.html',
  styleUrls: ['./blood-donor-list.component.css'],
})
export class BloodDonorListComponent implements OnInit {
  displayedColumns: string[] = [
    'name',
    'surname',
    'email',
    'address',
    'jmbg',
    'phoneNumber',
    'institution',
    'gender'
  ];

  bloodDonorsSearched: BloodDonorInfo[] = [];
  _searchForm: FormGroup = new FormGroup({
  name: new FormControl<string>(''),
  surname: new FormControl<string>(''),
  });

  pageSize : number = 5;
  resultsLength : number = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @Output() searchEvent = new EventEmitter();

  @ViewChild('paginator') paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('searchHtml') searchHtml!: Input;


  get searchForm() {
    return this._searchForm.controls;
  }

  constructor(
    private readonly bloodDonorService: BloodDonorService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page, this.searchEvent)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          //Ovde svoj servis metis
          return this.bloodDonorService.searchByNameAndUsername({
              name: this.searchForm['name'].value,
              surname: this.searchForm['surname'].value,
              page : this.paginator.pageIndex,
              pageSize: this.pageSize,
              sortType: this.parseSortDirection(this.sort.direction),
              sortByField: this.sort.active
          })
          .pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<BloodDonorInfo[]> = {
              content: [],
              count: 0,
            };
            return empty;
          }

          // Only refresh the result length if there is new data. In case of rate
          // limit errors, we do not want to reset the paginator to zero, as that
          // would prevent users from re-triggering requests.
          this.resultsLength = (data as PageDto<BloodDonorInfo>).count;
          return data;
        })
      )
      .subscribe((data) => (this.bloodDonorsSearched = (data as PageDto<BloodDonorInfo[]>).content));
  }

  search(): void {
    this.searchEvent.emit();
  }


  parseGender(gender: string) {
    if (gender === 'MALE') {
      return 'Male';
    }
    return 'Female';
  }

  parseSortDirection(direction : SortDirection){
      switch(direction)
      {
        case '': return SortType.None; break;
        case 'asc': return SortType.Asc; break;
        case 'desc': return SortType.Desc; break;
      }
  }
}
function observableOf(arg0: null): any {
  throw new Error('Function not implemented.');
}

