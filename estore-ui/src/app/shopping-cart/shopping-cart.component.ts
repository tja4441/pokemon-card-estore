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
  
  public shoppingCart: ShoppingCart | undefined
  public contents: [] = []

  constructor(private userService: UserService,
    private cartService: ShoppingCartService ) {
     }

  ngOnInit(): void {
    this.getShoppingCart();
  }

  getShoppingCart(): void{
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => this.shoppingCart = shoppingCart )
  }


  
  

}
