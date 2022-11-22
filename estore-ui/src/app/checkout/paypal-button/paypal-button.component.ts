import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { ShoppingCart } from '../../model/ShoppingCart';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { Location } from '@angular/common';
import { StatisticsService } from 'src/app/services/statistics.service';

declare var paypal: any;

@Component({
  selector: 'app-paypal-button',
  templateUrl: './paypal-button.component.html',
  styleUrls: ['./paypal-button.component.css']
})
export class PaypalButtonComponent implements OnInit {

  @Input() order!: ShoppingCart

  success = false;

  throwaway: any = null;

  public count = 0;

  @ViewChild('paypal', {static: true}) paypalElement!: ElementRef

  constructor(private cartService: ShoppingCartService,
              private route: Router,
              private userService: UserService,
              private statsService: StatisticsService,
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

        const sessionTime = self.userService.getSessionTime();
        self.statsService.updateUserSessionData(sessionTime)
        .subscribe(p => {
          this.throwaway = p
          self.statsService.updateUserStats(this.order)
          .subscribe(p => {
            this.throwaway = p
            self.statsService.updateStoreSessionData(sessionTime)
            .subscribe(p => {
              this.throwaway = p
              self.statsService.updateStoreStats(this.order)
              .subscribe(p => {
                this.throwaway = p
                self.cartService.checkout(self.order.id)
                .subscribe(shoppingCart => {
                  self.order = shoppingCart
                  self.userService.setLoginTime();
                })
              })
            })
          })
        })
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
