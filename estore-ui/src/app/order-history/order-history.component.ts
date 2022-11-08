import { Component, Input, OnInit } from '@angular/core';
import { OrderHistory } from '../order-history';
import { ShoppingCart } from '../ShoppingCart';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  @Input() order!: OrderHistory;
  cart: ShoppingCart

  constructor() {
    this.cart = this.order.cart
  }

  ngOnInit(): void { }
}
