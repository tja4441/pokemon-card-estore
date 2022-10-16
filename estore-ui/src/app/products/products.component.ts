import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from '../product'

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

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
    // if( isNaN(quantity) ) { return; }
    // if( isNaN(price) ) { return; }
    // price = Number(price);
    // if (!name) { return; }
    this.productService.addProduct({ name, quantity, price } as Product)
    .subscribe(product => {this.products.push(product)})
  }
}
