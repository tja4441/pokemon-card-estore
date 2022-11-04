import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ChangePass } from '../../model/changePass';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.css']
})
export class ChangePasswordFormComponent implements OnInit {
  public changeSucceeded = false
  public changeFailed = false

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  public changePassword(oldPass: String, newPass: String) {
    this.userService.changePass({oldPass: oldPass, newPass: newPass} as ChangePass)
      .subscribe(success => {
        if(success) {
          this.changeSucceeded = true
          this.changeFailed = false
        }
        else {
          this.changeFailed = true
          this.changeSucceeded = false
        }
      })
  }
}
