import { Component, Input, OnInit} from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-remove-product',
  templateUrl: './remove-product.component.html',
  styleUrls: ['./remove-product.component.css']
})
export class RemoveProductComponent implements OnInit {
  @Input() product: Product | undefined;
  constructor(private productService: ProductService, private router: Router) { }

  ngOnInit(): void {
  }

  removeProduct(id: number): void {
    this.productService.removeProduct(id).subscribe();
    window.location.reload()
  }
}
