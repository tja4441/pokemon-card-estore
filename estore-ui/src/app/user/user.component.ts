import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD
import { ActivatedRoute } from '@angular/router';
import { OrderHistory } from '../order-history';
import { OrderHistoryService } from '../order-history.service'
import { User } from '../user';
import { UserService } from '../user.service';
=======
import { UserService } from '../services/user.service';
>>>>>>> main

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public user: User
  public orderHistory: OrderHistory[] = []

<<<<<<< HEAD
  constructor(private route: ActivatedRoute, private orderHistoryService: OrderHistoryService, private userService: UserService) {
    this.user = userService.getUser()
    this.orderHistoryService.getOrdersByUserID(this.user.id).subscribe(p => this.orderHistory = p)
=======
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    const username = this.userService.getUser().UserName
    this.username = username ? username: ""
>>>>>>> main
  }

  ngOnInit(): void {}
}
