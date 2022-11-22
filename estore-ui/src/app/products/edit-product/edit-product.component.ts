import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  @Input() card?: Product
  @Output() changedProductEvent = new EventEmitter<Product>()
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  /**
   * changes this cards price and/or quantity in the backend and sends an event
   * so that the UI knows to change as well
   * @param price price to change to
   * @param quantity quantity to change to
   */
  setProduct(price: number, quantity: number): void {
    if(!this.card || isNaN(price) || isNaN(quantity)) return
    this.card.price = price
    this.card.quantity = quantity
    //uses productService to edit data on the backend
    this.productService.editProduct(this.card)
    .subscribe((p: Product) => {
      //after it has successfully changed on the backend emits event with the new product
      if(p.price == this.card?.price && p.quantity == this.card?.quantity) {
        this.changedProductEvent.emit(p)
      }
    })
  }
}
