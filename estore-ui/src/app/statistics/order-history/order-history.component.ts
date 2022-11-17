import { Component, Input, OnInit } from '@angular/core';
import { OrderHistory } from '../../model/order-history';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  @Input() orderHistory!: OrderHistory[];
  public empty: boolean = false

  constructor() {}

  ngOnInit(): void {
    if(this.orderHistory.length == 0) {
      this.empty = true
    }
  }
}
