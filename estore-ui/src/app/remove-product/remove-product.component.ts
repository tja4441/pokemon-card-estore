import { Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

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

  removeProduct(id: number): void {
    this.productService.removeProduct(id).subscribe(() =>{
      this.deletedItemEvent.emit(id)});
  }
}
