import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public EDIT = true
  public ACCOUNTS = false
  public STATS = false

  constructor() {}
  ngOnInit(): void {}

  public edit() {
    this.EDIT = true
    this.ACCOUNTS = false
    this.STATS = false
  }
  public account() {
    this.EDIT = false
    this.ACCOUNTS = true
    this.STATS = false
  }
  public stats() {
    this.EDIT = false
    this.ACCOUNTS = false
    this.STATS = true
  }
}
