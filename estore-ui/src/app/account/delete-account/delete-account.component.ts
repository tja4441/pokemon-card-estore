import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {
  public deleteFailed = false

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  public deleteAccount() {
    this.userService.deleteAccount().subscribe((success) => {
      if(success){
        this.userService.logout()
        this.router.navigate(["/login"])
      }
      else {
        this.deleteFailed = true;
      }
    })
  }
}
