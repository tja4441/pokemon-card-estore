import { Component, OnInit } from '@angular/core';
import { PassUser } from 'src/app/model/passUser';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-accounts',
  templateUrl: './admin-accounts.component.html',
  styleUrls: ['./admin-accounts.component.css']
})
export class AdminAccountsComponent implements OnInit {
  public attempted = false
  public emptyField = false
  public confirmDoesntMatch = false
  public createdSuccess = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  public createAdmin(username: String, password: String, confirm: String) {
    username = username.trim()
    password = password.trim()
    confirm = confirm.trim()
    if(username == "" || password == "" || confirm == ""){
      this.emptyField = true
      this.confirmDoesntMatch = false
      return
    }
    if(password != confirm) {
      this.confirmDoesntMatch = true
      this.emptyField = false
      return
    }
    let user = {UserName: username, Password: password} as PassUser
    this.userService.createAdmin(user).subscribe(user => {
      this.createdSuccess = user ? true: false
    })
  }
}
