import { Component, OnInit, Input } from '@angular/core';
import { Product, CardType } from '../../model/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {
  @Input() productsList!: Product[];
  @Input() selTypes!: CardType[];
  public isTypes: boolean = false;
  public displayed: boolean = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    //gets products from productService and then when they resolve sets productList
    if( !this.productsList ){
      this.getAllProducts();
    }
    if( this.selTypes.length != 0 ){
      this.isTypes = true;
    }
  }

  wasDisplayed(b: boolean){
    this.displayed = b;
  }

  getAllProducts(): void {
    this.productService.getProducts().subscribe(productsList => this.productsList = productsList)
  }

  deleteItem(id: number): void {
    this.productsList = this.productsList.filter((p: Product) => p.id != id)
  }
}
