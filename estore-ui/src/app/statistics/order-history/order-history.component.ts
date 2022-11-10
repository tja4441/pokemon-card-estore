import { Component, Input, OnInit } from '@angular/core';
import { OrderHistory } from '../../model/order-history';
import { OrderHistoryService } from '../../services/order-history.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  @Input() userID!: number;
   public orderHistory: OrderHistory[] = [];
   public empty: boolean = true

  constructor(private orderHistoryService: OrderHistoryService) {}

  ngOnInit(): void {
    this.orderHistoryService.getOrdersByUserID(this.userID).subscribe(p => this.orderHistory = p)
    if(this.orderHistory.length != 0) {
      this.empty = false
    }
  }
}
