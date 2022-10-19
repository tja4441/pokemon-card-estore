import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginFailed = false
  constructor(private userService: UserService, private logger: MessageService, private router: Router) { }

  ngOnInit(): void {}

  login(username: string): void {
    username = username.trim()
    if(!username) return

    this.userService.login(username)
      .subscribe(user=> {
        this.userService.setUser(user)

        let USER = this.userService.getUser()
        if(this.userService.isLoggedIn()) {
          this.logger.add(`Logged in as User{id: ${USER.id}, username: ${USER.UserName}}`)
          if(username == "admin") {
            this.router.navigate(['/admin'])
          }
          else {
            this.router.navigate(["/user", {username: username}])
          }
        }
        else this.loginFailed = true
      }) 
  }

  register(username: string) {
    username = username.trim()
    if(!username) return
    this.logger.add(`Registering User: ${username}`)

    this.userService.register({UserName: username, id: -1} as User)
      .subscribe(user => {
        this.userService.setUser(user)
        let USER = this.userService.getUser()
        if(this.userService.isLoggedIn()) {
          this.logger.add(`Registered new User{id: ${USER.id}, username: ${USER.UserName}}`)
          if(username == "admin") {
            this.router.navigate(['/admin'])
          }
          else {
            this.router.navigate(["/user", {username: username}])
          }
        }
        else this.loginFailed = true
      })
  }

}
