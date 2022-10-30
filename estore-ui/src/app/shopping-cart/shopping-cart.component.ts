import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
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

  constructor(private userService: UserService ) {
      
     }

  ngOnInit(): void {
    this.shoppingCart = this.getShoppingCart();
    this.contents = this.getContents();
  }

  getShoppingCart(): ShoppingCart {
    return this.userService.getCart();
    
    
  }

  getContents(): Product[] {
    return Array.from(this.shoppingCart.contents)
  }

}
