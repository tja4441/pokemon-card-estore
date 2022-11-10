import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router
} from '@angular/router';
import { UserService } from '../services/user.service';



@Injectable({providedIn: 'root'})
export class UserGuard implements CanActivate {
  constructor(private userService: UserService,
    private route: Router) {}

    canActivate(): boolean {
        if(this.userService.isAdmin()){
            this.route.navigate(['']);
            return false;
        }
        if(!this.userService.isLoggedIn()){
            this.route.navigate(['']);
            return false;
        }
      return true;
    }

}