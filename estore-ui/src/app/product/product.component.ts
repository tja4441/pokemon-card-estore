import { Component, Inject, OnInit } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  name: String;
  price: number;
  quantity: number;

  constructor(@Inject(ProductComponent) card: Product) { 
    this.name = card.name;
    this.price = card.price;
    this.quantity = card.quantity;
  }

  ngOnInit(): void {
  }

}
