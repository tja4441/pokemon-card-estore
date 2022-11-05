import { Component, OnInit, Type } from '@angular/core';

import { ProductService } from '../product.service';
import { CardType, CardTypeConverter, Product } from '../product'

@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {

  products: Product[] = [];
  public typeDict: any = {[CardType.DARK] : false,
                          [CardType.DRAGON] : false,
                          [CardType.ELECTRIC] : false,
                          [CardType.FAIRY] : false,
                          [CardType.FIGHTING] : false,
                          [CardType.FIRE] : false,
                          [CardType.GRASS] : false,
                          [CardType.NORMAL] : false,
                          [CardType.PSYCHIC] : false,
                          [CardType.STEEL] : false,
                          [CardType.WATER] : false,
                          [CardType.TRAINER] : false}
  public typeList: CardType[] = []

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  flipBool(typeString: string) {
    this.typeDict[typeString] = !this.typeDict[typeString]
    let type: CardType = CardTypeConverter.stringToType(typeString)
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
  add(name: string, types: CardType[], quantity: any, price: any): void {
    //removes whitespace
    name = name.trim();
    //tries to add product to the server using productService
    this.productService.addProduct({ name, types, quantity, price } as Product)
    //productService returns product on success which is added to UI/this.products
    .subscribe(product => this.products.push(product))
  }
}
