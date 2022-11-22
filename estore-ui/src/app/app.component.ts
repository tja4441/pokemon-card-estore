import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(public userService: UserService, 
    private router: Router){
  }
  ngOnInit(): void {
    this.userService.getState()
    this.userService.setLoginTime()
  }  

  redirect(){
    if(this.userService.isAdmin()) this.router.navigate(["/admin"])
    else this.router.navigate(["/user", {username: this.userService.username}])
  }
  isAdmin() : Boolean{
    return this.userService.isAdmin();
  }

  isLoggedInUser() : Boolean{
    return this.userService.isLoggedIn() && !this.userService.isAdmin();
  }
}
