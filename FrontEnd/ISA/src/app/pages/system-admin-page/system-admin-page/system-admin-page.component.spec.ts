import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemAdminPageComponent } from './system-admin-page.component';

describe('SystemAdminPageComponent', () => {
  let component: SystemAdminPageComponent;
  let fixture: ComponentFixture<SystemAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SystemAdminPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SystemAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
