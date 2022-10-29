import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {
  productsList: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    //gets products from productService and then when they resolve sets productList
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(productsList => this.productsList = productsList)
  }

  /**
   * deletes "all"(should really only ever be 1) products from the product list that have id "id"
   * @param id id of product to remove from list
   */
  deleteItem(id: number): void {
    this.productsList = this.productsList.filter((p: Product) => p.id != id)
  }
}
