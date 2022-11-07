import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../ShoppingCart';
import { UserService } from '../user.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  
  public shoppingCart!: ShoppingCart 

  constructor(private cartService: ShoppingCartService,
              private userService: UserService) { 
   
  }

  ngOnInit(): void {
    this.getCart();
  }

  getCart(){
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => this.shoppingCart = shoppingCart)
  }

}
