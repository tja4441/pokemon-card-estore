import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(public userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getState()
  }

  isLoggedInUser() : Boolean{
    return this.userService.isLoggedIn() && !this.userService.isAdmin();
  }

  getUsername() : String{
    return this.userService.getUser().UserName;
  }
}
