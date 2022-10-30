import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../ShoppingCart';
import { UserService } from '../user.service';


@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  public shoppingCart!: ShoppingCart 
  public contents: Product[] = []

  constructor(private userService: UserService,
    private cartService: ShoppingCartService,
    private route: Router ) {
      
     }

  ngOnInit(): void {
    this.getCart()
  }

  getContents(): Product[] {
    return Array.from(this.shoppingCart.contents)
  }

  getCart(){
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => {this.shoppingCart = shoppingCart
      this.contents = this.getContents()})
  }

  refresh(){
    this.route.navigate(["/user", {username: this.userService.username}])
  }


}
