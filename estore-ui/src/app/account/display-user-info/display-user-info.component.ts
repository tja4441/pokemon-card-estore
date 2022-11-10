import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-display-user-info',
  templateUrl: './display-user-info.component.html',
  styleUrls: ['./display-user-info.component.css']
})
export class DisplayUserInfoComponent implements OnInit {
  public username: String = ""
  public admin: String = ""

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.username = this.userService.getUser().UserName
    this.admin = this.userService.isAdmin() ? "True": "False"
  }


}
