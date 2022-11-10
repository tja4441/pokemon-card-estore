import { TestBed } from '@angular/core/testing';

import { OrderHistoryService } from './services/order-history.service';

describe('OrderHistoryService', () => {
  let service: OrderHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
