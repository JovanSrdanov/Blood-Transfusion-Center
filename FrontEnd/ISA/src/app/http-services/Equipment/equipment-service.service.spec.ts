import { TestBed } from '@angular/core/testing';

import { EquipmentService } from './equipment-service.service';

describe('EquipmentServiceService', () => {
  let service: EquipmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
