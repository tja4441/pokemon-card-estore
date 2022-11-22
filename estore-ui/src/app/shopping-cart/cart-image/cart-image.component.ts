import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-cart-image',
  templateUrl: './cart-image.component.html',
  styleUrls: ['./cart-image.component.css']
})
export class CartImageComponent implements OnInit {

  @Input() srcPath: number | undefined;
  @Input() scale: number | undefined;
  public cardHeight: number | undefined;
  public cardWidth: number | undefined;

  constructor() {}

  ngOnInit(): void {
      if(this.scale != undefined) {
      this.cardHeight = Math.sqrt(2) * this.scale
      this.cardWidth = this.scale
    } else {
      this.cardHeight = Math.sqrt(2)  * 100
      this.cardWidth = 100
    }
  }

}
