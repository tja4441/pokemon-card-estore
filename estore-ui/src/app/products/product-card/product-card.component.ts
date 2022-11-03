import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../../model/product';
import { UserService } from '../../services/user.service';

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

  /**
   * Sets edit to true if the useer is an admin
   * This flips the card over to the edit side
   */
  edit(){
    if(this.userService.isAdmin()) this.editing = true
  }

  /**
   * Changes this card to newCard.
   * Triggered on card edit change so it changes editing to to false,
   * flipping the card back to its display side
   * @param newCard 
   */
  itemChange(newCard: Product){
    this.card = newCard;
    this.editing = false
  }

  /**
   * Triggered by remove-product event emitter.
   * Triggers its own event emiter which goes back to the product list in order
   * to inform it that this card should be deleted.
   * @param id 
   */
  deleteItem(id: number) {
    this.deletedItemEvent.emit(id);
  }
}
