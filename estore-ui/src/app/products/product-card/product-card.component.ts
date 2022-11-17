import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';
import { Product } from '../../model/product';
import { UserService } from '../../services/user.service';



@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  @Input() card!: Product
  @Output() deletedItemEvent = new EventEmitter<number>()

  public turned: boolean = false;
  public source: String | undefined = undefined;
  public editing: boolean = false;
  image!: File 

  constructor(private userService: UserService,
              private imageService: ImageService) {
               }

  ngOnInit(): void {
    if (this.card != undefined) {
      this.imageService.getImageSrc(this.card.name).subscribe(source => {
        this.source = source;
      });
    }
    
  }

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
    this.turned = false;
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

  onFileChange(event: any): void {
    this.image = event.target.files[0];
  }

  uploadImage(): void {

    let name = this.card.name;

    name = name + ".png";
    if (this.image) {
      const image = new File([this.image], name, {type: this.image.type});
      this.imageService.uploadImage(image).subscribe(_ => window.location.reload())
    }
  }

  turn(){
    if(!this.editing) {
      this.turned = !this.turned
      this.edit()
    }
  }

  notAdmin(): Boolean{
    return this.userService.isLoggedIn() && !this.userService.isAdmin()
  }
}
