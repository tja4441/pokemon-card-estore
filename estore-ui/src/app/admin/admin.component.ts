import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public EDIT = true
  public ACCOUNTS = false

  constructor() {}
  ngOnInit(): void {}

  public edit() {
    this.EDIT = true
    this.ACCOUNTS = false
  }
  public account() {
    this.EDIT = false
    this.ACCOUNTS = true
  }
}
