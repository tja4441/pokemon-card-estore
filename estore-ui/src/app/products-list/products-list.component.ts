import { Component, Input, OnInit } from '@angular/core';
import { CardType, Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {
  @Input() productsList?: Product[];
  @Input() selTypes!: CardType[];
  public isTypes: boolean = false;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    //gets products from productService and then when they resolve sets productList
    if( !this.productsList ){
      this.getAllProducts();
    }
    if( this.selTypes.length != 0 ){
      this.isTypes = true;
    }
  }

  getAllProducts(): void {
    this.productService.getProducts().subscribe(productsList => this.productsList = productsList)
  }
}
