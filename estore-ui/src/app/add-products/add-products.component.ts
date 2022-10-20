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

  /**
   * Queries server for product list and stores in this.products
   */
  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  /**
   * adds a new card to the database and then changes this.products to reflect change
   * @param name name of product
   * @param quantity quantity of product
   * @param price price of the card
   */
  add(name: string, quantity: any, price: any): void {
    //removes whitespace
    name = name.trim();
    //tries to add product to the server using productService
    this.productService.addProduct({ name, quantity, price } as Product)
    //productService returns product on success which is added to UI/this.products
    .subscribe(product => this.products.push(product))
  }
}
