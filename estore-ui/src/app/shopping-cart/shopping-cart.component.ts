import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ShoppingCartService } from '../services/shopping-cart.service';
import { ShoppingCart } from '../model/ShoppingCart';
import { UserService } from '../services/user.service';


@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  public shoppingCart!: ShoppingCart 
  public contents: Product[] = []
  public nothingChangedWhileGone!: boolean;

  constructor(private userService: UserService,
    private cartService: ShoppingCartService, ) {}

  ngOnInit(): void {
    this.refreshCart()
    this.getCartInit()
  }

  getContents(): Product[] {
    return Array.from(this.shoppingCart.contents)
  }

  getCartInit(){
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => {this.shoppingCart = shoppingCart
                                this.contents = this.getContents()})
  }

  getCart(){
    this.cartService.getCart(this.userService.getUser().id)
    .subscribe(shoppingCart => {this.shoppingCart = shoppingCart
                                this.contents = this.getContents()
                                this.nothingChangedWhileGone = true})
  }

  refreshCart(){
    this.cartService.refreshCart(this.userService.getId())
    .subscribe(boolean => this.nothingChangedWhileGone = boolean)
  }
}
