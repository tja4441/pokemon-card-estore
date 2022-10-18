import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loggedIn = false
  public loginFailed = false
  constructor(private userService: UserService, private logger: MessageService) { }

  ngOnInit(): void {}

  login(username: string) {
    username = username.trim()
    if(!username) return

    this.userService.login(username)
      .subscribe(user=> {
        this.userService.setUser(user)
        this.loggedIn = this.userService.isLoggedIn()

        let USER = this.userService.getUser()
        this.logger.add(`Logged in as User{id: ${USER.id}, username: ${USER.UserName}}`)
        if(!this.loggedIn) this.loginFailed = true
      })
  }

  register(username: string) {
    username = username.trim()
    if(!username) return
    this.logger.add(`Registering User: ${username}`)

    this.userService.register({UserName: username, id: -1} as User)
      .subscribe(user => {
        this.userService.setUser(user)
        this.loggedIn = this.userService.isLoggedIn()

        let USER = this.userService.getUser()
        this.logger.add(`Registered new User{id: ${USER.id}, username: ${USER.UserName}}`)
        if(!this.loggedIn) {
          this.loginFailed = true
          this.logger.add("Error: Registration Failed!")
        }
      })
  }

}
