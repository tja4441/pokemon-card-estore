import { Component, OnInit } from '@angular/core';
import { render } from 'creditcardpayments/creditCardPayments';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  constructor() { 
    render(
      {
        id: "#myPayPalButtons",
        currency: "USD",
        value: "100.00",
        onApprove: (details) => {
          alert("Hello!");
        }
      }
    );
  }

  ngOnInit(): void {
    
  }
}
