import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
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

  @ViewChild('paypal', {static: true}) paypalElement!: ElementRef

  constructor(private cartService: ShoppingCartService,
              private userService: UserService) { }

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
        self.cartService.checkout(self.order.id)
          .subscribe(shoppingCart => this.order = shoppingCart)
         
      },

      onError: (err: any) => {
        alert('Payment Failed')
      },
    })
    .render(this.paypalElement.nativeElement);
    
  }

}
