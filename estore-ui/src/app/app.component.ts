import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public title = 'Pokémon Card Store'
  constructor(public userService: UserService, 
    private router: Router){
  }
  ngOnInit(): void {
    this.userService.getCookie()
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
