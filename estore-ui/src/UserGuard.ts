import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot, 
  UrlTree,
  Router
} from '@angular/router';
import { CookieOptions, CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { UserService } from './app/user.service';



@Injectable({providedIn: 'root'})
export class UserGuard implements CanActivate {
  constructor(private userService: UserService,
    private cookieService: CookieService,
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