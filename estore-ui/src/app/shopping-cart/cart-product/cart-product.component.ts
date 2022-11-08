import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { ShoppingCart } from '../../model/ShoppingCart';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-cart-product',
  templateUrl: './cart-product.component.html',
  styleUrls: ['./cart-product.component.css']
})
export class CartProductComponent implements OnInit {

  @Input() product!: Product
  @Output() shoppingCart = new EventEmitter()
  invProduct!: Product

  constructor(private userService: UserService,
    private cartService: ShoppingCartService,
    private productService: ProductService) { }

  ngOnInit(): void {
    this.getProduct()
  }


  addToCart(): void{
    this.cartService.addToCart(this.userService.getUser().id, this.invProduct )
    .subscribe(shoppingCart => this.shoppingCart.emit())
  }

  deleteFromCart(): void{
    this.cartService.deleteFromCart(this.userService.getUser().id, this.invProduct)
    .subscribe(shoppingCart => this.shoppingCart.emit())
  }

  getProduct(){
    this.productService.getProduct(this.product.id).subscribe(
    invproduct => this.invProduct = invproduct)
  }

}
