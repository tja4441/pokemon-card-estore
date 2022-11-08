import { Component, Input, OnInit } from '@angular/core';
import { OrderHistory } from '../order-history';
import { OrderHistoryService } from '../order-history.service';
import { ShoppingCart } from '../ShoppingCart';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  @Input() userID!: number;
   public orderHistory?: OrderHistory[];

  constructor(private orderHistoryService: OrderHistoryService) {
    
  }

  ngOnInit(): void {
    this.orderHistoryService.getOrdersByUserID(this.userID).subscribe(p => this.orderHistory = p)
  }
}
