import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot, 
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user.service';


@Injectable({providedIn: 'root'})
export class AdminGuard implements CanActivate {
  constructor(private userService: UserService,
              private route: Router) {}

    canActivate(): boolean {
      if(this.userService.isAdmin()){
        return true;
      }
      this.route.navigate(['']);
      return false;
    }

}




