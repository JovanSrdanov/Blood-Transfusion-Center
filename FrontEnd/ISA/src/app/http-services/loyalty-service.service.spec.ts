import { TestBed } from '@angular/core/testing';

import { LoyaltyServiceService } from './loyalty-service.service';

describe('LoyaltyServiceService', () => {
  let service: LoyaltyServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoyaltyServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
