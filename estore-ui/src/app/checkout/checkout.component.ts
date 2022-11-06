import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  name: String | undefined

  checkout = false

  constructor(private route: Router) { }

  ngOnInit(): void {

  }
  
  CheckedOut(): Boolean{
    this.checkout = true
    return this.checkout
  }


  



}
