import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../product';
import { UserService } from '../user.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  @Input() card: Product | undefined;
  @Output() deletedItemEvent = new EventEmitter<number>()
  public editing: boolean = false;
  constructor(private userService: UserService) { }

  ngOnInit(): void {}

  changeFace(){
    if(this.userService.isAdmin()) this.editing = true
  }

  submit(){
    this.editing = false
  }

  itemChange(newProduct: Product){
    this.card = newProduct;
    this.editing = false
  }

  deleteItem(id: number) {
    this.deletedItemEvent.emit(id);
  }
}
