import { Component, OnInit, Input } from '@angular/core';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  // should be passed in name, price, and quantity as input when created
  // in order to avoid erroneous calls to backend
  @Input() name?: string
  @Input() price?: number
  @Input() quantity?: number
  constructor(private productService: ProductService) { }

  ngOnInit(): void {}

  setPrice(): void{
    
  }
}
