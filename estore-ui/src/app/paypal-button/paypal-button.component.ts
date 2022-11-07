import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../ShoppingCart';
import { UserService } from '../user.service';

declare var paypal: any;

@Component({
  selector: 'app-paypal-button',
  templateUrl: './paypal-button.component.html',
  styleUrls: ['./paypal-button.component.css']
})
export class PaypalButtonComponent implements OnInit {

  @Input() order!: ShoppingCart

  canCheckOut = true;

  @ViewChild('paypal', {static: true}) paypalElement!: ElementRef

  constructor(private cartService: ShoppingCartService,
              private userService: UserService,
              private route: Router) { }
  
  ngOnInit(): void {
    const self = this;
    paypal
    .Buttons({
      onClick: () => {
        self.cartService.refreshCart(self.userService.getId())
      .subscribe(boolean => self.canCheckOut = boolean)
      if(!this.canCheckOut){
        self.route.navigate([''])
      }
      },
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
        self.cartService.checkout(self.order.id)
          .subscribe(shoppingCart => self.order = shoppingCart)

        },
      
      onError: (err: any) => {
        alert('Payment Failed')
        self.route.navigate([''])
      },
    })
    .render(this.paypalElement.nativeElement);
    
  }

}
