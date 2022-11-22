import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderHistory } from '../model/order-history';
import { OrderHistoryService } from '../services/order-history.service'
import { User } from '../model/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public user: User
  public orderHistory: OrderHistory[] = []
  public ACCOUNT = true
  public HISTORY = false

  constructor(private userService: UserService, private orderHistoryService: OrderHistoryService) { 
    this.user = this.userService.getUser()
    this.orderHistoryService.getOrdersByUserID(this.user.id).subscribe(p => this.orderHistory = p)
  }

  ngOnInit(): void { }

  public account() {
    this.ACCOUNT = true
    this.HISTORY = false
  }
  public history() {
    this.ACCOUNT = false
    this.HISTORY = true
  }
}