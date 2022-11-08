import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public username: string = ""

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    const username = this.userService.getUser().UserName
    this.username = username ? username: ""
  }
}
