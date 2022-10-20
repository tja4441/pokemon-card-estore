import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, Observable, Subject, switchMap } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  obsProducts$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  constructor(private productService: ProductService) {}

  search(term: string): void {
    this.searchTerms.next(term.trim());
  }

  ngOnInit(): void {
    this.obsProducts$! = this.searchTerms.pipe(
      //time to wait for another input before actually triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    );
  }
}