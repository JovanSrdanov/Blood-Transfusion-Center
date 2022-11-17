import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { LoyaltyServiceService } from 'src/app/http-services/loyalty-service.service';
import { CouponInfo } from 'src/app/model/loyalty/coupon-info';

@Component({
  selector: 'app-loyalty-table',
  templateUrl: './loyalty-table.component.html',
  styleUrls: ['./loyalty-table.component.css']
})
export class LoyaltyTableComponent implements OnInit {
  displayedColumns = ['discount', 'desc'];
  dataSource: CouponInfo[] = [];
  categoryName: string = "";
  private eventsSubscription!: Subscription;

  @Input() events!: Observable<number>;


  constructor(private readonly loyaltyService: LoyaltyServiceService) { }

  ngOnInit(): void {
    this.eventsSubscription = this.events.subscribe((data) => this.eventListener(data));
  }

  eventListener(points: number): void {
    this.loyaltyService.fetchLoyalty(points).subscribe((res) => {
      this.categoryName = res.category;
      this.dataSource = res.coupons;
      console.log("Datasource:", this.dataSource);
    });
  }



}
