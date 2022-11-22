import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../model/user';
import { Location } from '@angular/common';
import { MessageService } from '../services/message.service';
import { Router } from '@angular/router';
import { PassUser } from '../model/passUser';
import { ShoppingCartService } from '../services/shopping-cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public username = ""
  public loginFailed = false
  public registerFailed = false
  constructor(private userService: UserService,
     private logger: MessageService, 
     private router: Router,
     private location: Location,
     private cartService: ShoppingCartService) { }

  ngOnInit(): void {
    //when home page loads looks to see if the user is logged in
    this.username = this.userService.getUser().UserName;
  }

  /**
   * sets the "Global" user to this user in userService then redirects to home
   * if the user is valid and sets loginFailed to true otherwise
   * @param user user that has been added and retrieved from the backend
   */
  private addedUser(user: User){
    //sets user property of userservice for login persistance accross multiple routes
    this.userService.setUser(user)
    this.userService.setLoginTime()
    if(this.userService.isLoggedIn()) this.goHome()
    else this.loginFailed = true
  }

  private addedRegister(user: User){
    //sets user property of userservice for login persistance accross multiple routes
    this.userService.setUser(user)
    this.userService.setLoginTime();
    if(this.userService.isLoggedIn()) this.goHome()
    else this.registerFailed = true
  }
  
  /**
   * Tries to log in user. However, if the user tries to log in
   * with a username thats not in the database sets loginFailed to true
   * @param username entered username
   */
  login(username: string, password: string): void {
    //removes whitespace from name during as preprocessing
    username = username.trim()
    //does nothing if user entered nothing or just whitespace
    if(!username || !password ) return
    //checks if user is in the backend
    this.logger.add(`Logging in as User: ${username}`)
    this.userService.login(username, password)
      .subscribe(user => this.addedUser(user))
  }

  register(username: string, password: string, confirm: string): void {
    this.logger.add(`Trying to Register User: ${username} Pass: ${password} Confirm: ${password}`)
    //removes whitespace from name during as preprocessing
    username = username.trim()
    //does nothing if user entered nothing or just whitespace
    if(!username || !password || !confirm) return
    if(password != confirm) return
    this.logger.add(`username not empty, password and confirm match`)
    this.logger.add(`Registering User: ${username}`)
    this.userService.register({UserName: username, id: -1, Password: password} as PassUser)
      .subscribe(user => this.addedRegister(user))
  }

  /**
   * logs out "Global"(userService) user and sets username to an empty string to
   * swap div back to login screen
   */
  logout(): void {
    this.userService.logout()
    this.username = "";
  }
  
  goHome(): void {
    this.router.navigate([""])
  }

  goBack(): void {
    this.location.back();
  }
}
