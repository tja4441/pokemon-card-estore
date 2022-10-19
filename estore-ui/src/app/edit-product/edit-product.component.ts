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
  @Input() product?: Product
  public changed = false
  public hidden = false
  constructor(private productService: ProductService) { }

  ngOnInit(): void {}

  setPrice(): void{
    if(!this.product) return;
    else{
      this.productService.editProduct(this.product)
      .subscribe(p => {
        if(p.price == this.product?.price && p.quantity == this.product?.quantity) this.changed = true})
    }
  }

  close(): void{
    this.hidden = true
  }
}
