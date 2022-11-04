import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public EDIT = true
  public STATS = false
  public ACCOUNTS = false

  constructor() {}
  ngOnInit(): void {}

  public edit() {
    this.EDIT = true
    this.STATS = false
    this.ACCOUNTS = false
  }
  public stats() {
    this.EDIT = false
    this.STATS = true
    this.ACCOUNTS = false
  }
  public account() {
    this.EDIT = false
    this.STATS = false
    this.ACCOUNTS = true
  }
}
