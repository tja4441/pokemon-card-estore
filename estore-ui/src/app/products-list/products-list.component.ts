import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../product';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent implements OnInit {
  @Input() observableList: Observable<Product[]> | undefined;
  @Input() isAdmin: Boolean | undefined;
  productsList: Product[] | undefined;

  constructor() { }

  ngOnInit(): void { 
    if(this.observableList != undefined){
      this.observableList.subscribe(products => this.productsList = products);
    }
  }

  deleteItem(id: number): void {
    if(!this.productsList) return
    this.productsList = this.productsList.filter((p: Product) => p.id != id)
  }
}
