import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { ShoppingCart } from '../../model/ShoppingCart';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { Location } from '@angular/common';

declare var paypal: any;

@Component({
  selector: 'app-paypal-button',
  templateUrl: './paypal-button.component.html',
  styleUrls: ['./paypal-button.component.css']
})
export class PaypalButtonComponent implements OnInit {

  @Input() order!: ShoppingCart

  success = false;

  public count = 0;

  @ViewChild('paypal', {static: true}) paypalElement!: ElementRef

  constructor(private cartService: ShoppingCartService,
              private route: Router,
              private userService: UserService,
              private location: Location) { }
  
  ngOnInit(): void {
    const self = this;
    paypal
    .Buttons({
      createOrder: (data: any, actions: any) => {
        return actions.order.create({
          purchase_units: [
            {
              amount: {
                currency_code: 'USD',
                value: self.order.totalPrice,
              },
            },
          ],
        });
      },
      onApprove: async (data: any, actions: any) => {

        const payment = await actions.order.capture();
      
        this.success = true;

        self.cartService.checkout(self.order.id)
          .subscribe(shoppingCart => self.order = shoppingCart)
        },
      
      onError: (err: any) => {
        alert('Payment Failed')
        this.location.back()
      },
    })
    .render(this.paypalElement.nativeElement);
    
  }

  getCount(): Boolean{
    return this.count < 1
  }

  redirect(){
    this.count +=1
    alert('Payment Success')
    this.route.navigate([''])
  }

  

}
