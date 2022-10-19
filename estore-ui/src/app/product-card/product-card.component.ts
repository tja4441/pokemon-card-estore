import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  @Input() card: Product | undefined;
  public editing: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  changeFace(){
    this.editing = !this.editing
  }
}
