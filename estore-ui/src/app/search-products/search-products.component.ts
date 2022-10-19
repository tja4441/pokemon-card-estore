import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  private productService: ProductService;
  searchForList: Observable<Product[]> | undefined;

  constructor(productService: ProductService) {
    this.productService = productService;
  }

  getProductsByName(name: String): void {
    this.searchForList = this.productService.getProductsByString(name);
  }

  ngOnInit(): void {
  }

}
