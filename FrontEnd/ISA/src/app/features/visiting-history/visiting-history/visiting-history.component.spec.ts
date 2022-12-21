import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitingHistoryComponent } from './visiting-history.component';

describe('VisitingHistoryComponent', () => {
  let component: VisitingHistoryComponent;
  let fixture: ComponentFixture<VisitingHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitingHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisitingHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
