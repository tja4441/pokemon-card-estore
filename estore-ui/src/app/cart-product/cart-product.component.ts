import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
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
  @Output() shoppingCartAdd = new EventEmitter<ShoppingCart>()

  constructor(private userService: UserService,
    private cartService: ShoppingCartService) { }

  ngOnInit(): void {
  }


  addToCart(): void{
    this.cartService.addToCart(this.userService.getUser().id, this.product)
    .subscribe(shoppingCart => {this.shoppingCartAdd.emit(shoppingCart)
        this.cartService.replaceCart(shoppingCart)})
  }

}
