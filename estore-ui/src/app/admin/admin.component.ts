import { Component, OnInit } from '@angular/core';
var express = require("express");
var app = express();
var request = require("request");

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  name: string = 'name'
  quantity: number = 1
  price: number = 0.00

  constructor() { }

  ngOnInit(): void {
  }

  addCard(name: string, quantity: number, price: number) {
    app.post("/products", 
    function(){request("http://localhost:8080/products", function(){})});
  }
}