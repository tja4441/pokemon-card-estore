import { Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../model/product';

@Component({
  selector: 'app-remove-product',
  templateUrl: './remove-product.component.html',
  styleUrls: ['./remove-product.component.css']
})
export class RemoveProductComponent implements OnInit {
  @Input() product: Product | undefined;
  @Output() deletedItemEvent = new EventEmitter<number>()
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  /**
   * removes a product with id "id" from the database and sends an event up to product card that holds this
   * @param id the id of product to be removed
   */
  removeProduct(id: number): void {
    this.productService.removeProduct(id).subscribe(() =>{
      this.deletedItemEvent.emit(id)});
  }
}
