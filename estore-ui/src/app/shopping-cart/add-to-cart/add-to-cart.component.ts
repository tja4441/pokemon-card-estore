import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css']
})
export class AddToCartComponent implements OnInit {

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

  getProduct(){
    this.productService.getProduct(this.product.id).subscribe(
    invproduct => this.invProduct = invproduct)
  }
}
