import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() card: Product | undefined;
  id: number;

  constructor() {
    if(this.card) {
      this.id = this.card.id;
    }
    else {
      this.id = 0
    }
  }

  ngOnInit(): void {
  }
}
