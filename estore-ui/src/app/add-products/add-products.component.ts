import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product, CardType } from '../product'

@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {

  products: Product[] = [];
  public typeDictAdd: any = {"DARK" : false,
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
  public typeListAdd: CardType[] = []

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  handleClick(type: keyof typeof CardType) {
    if((this.typeListAdd.length < 2) || this.typeDictAdd[type]) {
        this.flipBool(CardType[type])
    }
    else{
      alert("You can Only Select 2 Types")
    }
    let checkBox = document.getElementById(type) as HTMLInputElement
    if(checkBox) checkBox.checked = this.typeDictAdd[type]
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDictAdd[typeString] = !this.typeDictAdd[typeString]
    if(this.typeDictAdd[typeString]) this.typeListAdd.push(type)
    else this.typeListAdd.splice(this.typeListAdd.indexOf(type), 1)
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
    let product: Product = new Product(0, name, types, quantity, price)
    this.productService.addProduct(product)
    //productService returns product on success which is added to UI/this.products
    .subscribe(product => this.products.push(product))
  }
}
