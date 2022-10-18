import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from '../product'

@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  add(name: string, quantity: any, price: any): void {
    name = name.trim();
    this.productService.addProduct({ name, quantity, price } as Product)
    .subscribe(product => {this.products.push(product)})
  }
}
