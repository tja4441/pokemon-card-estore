import { Component, OnInit } from '@angular/core';
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
    private cartService: ShoppingCartService ) {
      
     }

  ngOnInit(): void {
    let cart = this.cartService.getCart()
    if(cart){this.shoppingCart = cart }
    this.contents = this.getContents();
  }

  

  getContents(): Product[] {
    return Array.from(this.shoppingCart.contents)
  }

  updateCart(shoppingCart: ShoppingCart){
    this.shoppingCart = shoppingCart
  }

}
