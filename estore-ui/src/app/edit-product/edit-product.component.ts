import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  // should be passed in name, price, and quantity as input when created
  // in order to avoid erroneous calls to backend
  @Input() card?: Product
  @Output() changedProductEvent = new EventEmitter<Product>()
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  setPrice(price: number, quantity: number): void {
    console.log(this.card)
    console.log(price)
    console.log(quantity)
    if(!this.card || isNaN(price) || isNaN(quantity)) return
    this.card.price = price
    this.card.quantity = quantity
    this.productService.editProduct(this.card)
    .subscribe((p: Product) => {
      if(p.price == this.card?.price && p.quantity == this.card?.quantity) this.changedProductEvent.emit(p)
    })
  }
}
