import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelicopterMapComponent } from './helicopter-map.component';

describe('HelicopterMapComponent', () => {
  let component: HelicopterMapComponent;
  let fixture: ComponentFixture<HelicopterMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HelicopterMapComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelicopterMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
