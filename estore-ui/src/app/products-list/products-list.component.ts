import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {
  @Input() observableList: Observable<Product[]> | undefined = undefined;
  productsList: Product[] | undefined;

  constructor(private productService: ProductService) { }

  ngOnInit(): void { 
    if(this.observableList != undefined){
      this.observableList.subscribe(products => this.productsList = products);
    }
  }
}
