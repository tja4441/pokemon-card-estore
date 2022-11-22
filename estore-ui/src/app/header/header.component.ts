import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor( public userService: UserService,
    private router: Router ) { }

  ngOnInit(): void {
    this.userService.getState()
  }

  redirect(dest: string): void {
    this.router.navigate([dest]);
  }

  isAdmin() : Boolean{
    return this.userService.isAdmin();
  }

  isLoggedInUser() : Boolean{
    return this.userService.isLoggedIn() && !this.userService.isAdmin();
  }

}
