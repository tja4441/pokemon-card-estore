import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-card-image',
  templateUrl: './card-image.component.html',
  styleUrls: ['./card-image.component.css']
})
export class CardImageComponent implements OnInit {

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
