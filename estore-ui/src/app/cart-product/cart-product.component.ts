import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
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
  @Output() shoppingCartAdd = new EventEmitter()
  invProduct!: Product

  constructor(private userService: UserService,
    private cartService: ShoppingCartService,
    private productService: ProductService) { }

  ngOnInit(): void {
    this.getProduct()
  }


  addToCart(): void{
    this.cartService.addToCart(this.userService.getUser().id, this.invProduct )
    .subscribe(shoppingCart => this.shoppingCartAdd.emit())
  }

  getProduct(){
    this.productService.getProduct(this.product.id).subscribe(
    invproduct => this.invProduct = invproduct)
  }

}
