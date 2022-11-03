import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot, 
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from './user.service';


@Injectable({providedIn: 'root'})
export class CanActivateRouteGuard implements CanActivate {
  constructor(private userService: UserService,
              private route: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
      if(this.userService.isAdmin()){
        return true;
      }
      this.route.navigate(['']);
      return false;
    }

}




