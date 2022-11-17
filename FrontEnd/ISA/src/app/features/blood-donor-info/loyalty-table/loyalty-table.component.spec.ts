import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltyTableComponent } from './loyalty-table.component';

describe('LoyaltyTableComponent', () => {
  let component: LoyaltyTableComponent;
  let fixture: ComponentFixture<LoyaltyTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoyaltyTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoyaltyTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
