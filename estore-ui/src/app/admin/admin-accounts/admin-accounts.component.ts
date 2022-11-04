import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-accounts',
  templateUrl: './admin-accounts.component.html',
  styleUrls: ['./admin-accounts.component.css']
})
export class AdminAccountsComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  public createAdmin(username: String, password: String) {
    //this.userService.createAdmin(username, password)
  }
}
