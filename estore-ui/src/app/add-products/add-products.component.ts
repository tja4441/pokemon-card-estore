import { Component, OnInit, Type } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from '../product'

@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {

  products: Product[] = [];
  public typeDict: any = {"DARK" : false,
                          "DRAGON" : false,
                          "ELECTRIC" : false,
                          "FAIRY" : false,
                          "FIGHTING" : false,
                          "FIRE" : false,
                          "GRASS" : false,
                          "NORMAL" : false,
                          "PSYCHIC" : false,
                          "STEEL" : false,
                          "WATER" : false,
                          "TRAINER" : false}
  public typeList: string[] = []

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  flipBool(type: string) {
    this.typeDict[type] = !this.typeDict[type]
    if(Object.values(this.typeDict).includes(type)) this.typeList.push(type)
    else this.typeList.splice(this.typeList.indexOf(type), 1)
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
   * @param types type of the product
   * @param quantity quantity of product
   * @param price price of the card
   */
  add(name: string, types: string[], quantity: any, price: any): void {
    //removes whitespace
    name = name.trim();
    //tries to add product to the server using productService
    this.productService.addProduct({ name, types, quantity, price } as Product)
    //productService returns product on success which is added to UI/this.products
    .subscribe(product => this.products.push(product))
  }
}
