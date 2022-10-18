import { Component, OnInit, Input } from '@angular/core';
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
  @Input() id?: number
  @Input() name?: string
  @Input() price?: number
  @Input() quantity?: number
  public changed = false
  public hidden = false
  constructor(private productService: ProductService) { }

  ngOnInit(): void {}

  setPrice(): void{
    if(this.id == undefined || 
      this.name == undefined ||
      this.price == undefined ||
      this.quantity == undefined) return;
    let product: Product = 
      {
        id: this.id, 
        name: this.name, 
        price: this.price, 
        quantity: this.quantity
      }
    this.productService.editProduct(product)
      .subscribe(p => {
        if(p.price == this.price && p.quantity == this.quantity) this.changed = true})
  }

  close(): void{
    this.hidden = true
  }
}
