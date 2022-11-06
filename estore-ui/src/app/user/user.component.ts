import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderHistory } from '../order-history';
import { OrderHistoryService } from '../order-history.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public user: User
  public orderHistory: OrderHistory[] = []

  constructor(private route: ActivatedRoute, private orderHistoryService: OrderHistoryService, private userService: UserService) {
    this.user = userService.getUser()
    this.orderHistoryService.getOrdersByUserID(this.user.id).subscribe(p => this.orderHistory = p)
  }

  ngOnInit(): void {}
}
