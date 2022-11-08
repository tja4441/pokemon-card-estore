import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShoppingCartService } from '../services/shopping-cart.service';
import { ShoppingCart } from '../model/ShoppingCart';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  
  public shoppingCart!: ShoppingCart
  public count = 0;
  
  canCheckOut = true;

  constructor(private cartService: ShoppingCartService,
              private userService: UserService,
              private route: Router) { 
   
  }

  ngOnInit(): void {
    this.refreshCart();
    this.getCart();
  }

  getCart(){
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => this.shoppingCart = shoppingCart)
  }

  refreshCart(){
    this.cartService.refreshCart(this.userService.getId())
    .subscribe(boolean => this.canCheckOut = boolean)
  }

  getStatus(): Boolean{
    return this.canCheckOut
  }

  backToCart(){
    this.count +=1;
    alert("One or more items in your cart have changed or are no longer available")
    this.route.navigate(["/user", {username: this.userService.username}])
  }

  getCount(): Boolean{
    return this.count < 1
  }

}
