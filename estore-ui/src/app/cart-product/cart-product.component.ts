import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../ShoppingCart';
import { UserService } from '../user.service';

@Component({
  selector: 'app-cart-product',
  templateUrl: './cart-product.component.html',
  styleUrls: ['./cart-product.component.css']
})
export class CartProductComponent implements OnInit {

  @Input() product!: Product
  @Input() shoppingCart!: ShoppingCart

  constructor(private userService: UserService,
    private cartService: ShoppingCartService) { }

  ngOnInit(): void {
  }


  addToCart(): void{
    this.cartService.addToCart(this.userService.getUser().id, this.product)
    .subscribe(shoppingCart => this.shoppingCart = shoppingCart)
  }

}
