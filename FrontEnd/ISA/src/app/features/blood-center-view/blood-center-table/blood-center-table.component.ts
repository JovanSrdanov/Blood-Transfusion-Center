import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import {
  catchError,
  merge,
  startWith,
  switchMap,
  Observable,
  of as observableOf,
  map,
} from 'rxjs';
import { BloodCenterService } from 'src/app/http-services/blood-center.service';
import { BloodCenterBasicInfo } from 'src/app/model/blood-center/blood-center-basic-info';
import { PageDto } from 'src/app/model/PageDto';

@Component({
  selector: 'app-blood-center-table',
  templateUrl: './blood-center-table.component.html',
  styleUrls: ['./blood-center-table.component.css'],
})
export class BloodCenterTableComponent implements OnInit {
  displayedColumns = [
    'name',
    'address.country',
    'address.city',
    'address.street',
    'rating',
    'seePremade'
  ];
  dataSource: BloodCenterBasicInfo[] = [];
  public tableDataSource: MatTableDataSource<BloodCenterBasicInfo[]> =
    new MatTableDataSource<BloodCenterBasicInfo[]>([]);
  //mozda treb @Input() ispred searchInput-a
  searchInput: string = '';
  @Output() searchEvent = new EventEmitter();

  @ViewChild('paginator') paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('searchHtml') searchHtml!: Input;

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  //Stefan dodao
  @Input() set tableData(data: any[]) {
    this.setTableDataSource(data);
  }
  //Stefan dodao

  constructor(private readonly bloodCenterService: BloodCenterService, private readonly router: Router) { }

  ngOnInit(): void { }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page, this.searchEvent)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.bloodCenterService!.getPagableBloodCenters(
            this.paginator.pageIndex,
            this.searchInput,
            this.sort.direction,
            this.sort.active
          ).pipe(catchError(() => observableOf(null)));
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;

          if (data === null) {
            let empty: PageDto<BloodCenterBasicInfo[]> = {
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
      .subscribe((data) => (this.dataSource = data.content));
  }

  setTableDataSource(data: any) {
    this.tableDataSource = new MatTableDataSource<BloodCenterBasicInfo[]>(data);
    this.tableDataSource.paginator = this.paginator;
    this.tableDataSource.sort = this.sort;
  }

  doSearch(): void {
    this.searchEvent.emit();
  }

  seeAppointments(id: string): void {
    this.router.navigate(['blood-donor/premade-appointments/' + id]);

  }
}
