import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() card: Product | undefined = undefined;

  constructor() { }

  isUndefined(): Boolean {
    return this.card == undefined;
  }

  ngOnInit(): void {
  }

}
