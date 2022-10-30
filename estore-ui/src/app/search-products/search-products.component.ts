import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, Subject, switchMap } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  private searchTerms = new Subject<string>();
  private typeTerms = new Subject<string>();
  public empty = false
  constructor(private productService: ProductService) {}

  search(term: string, type: string): void {
    term = term.trim()
    type = type.trim()
    if(term || type) this.empty = false
    else this.empty = true
    this.searchTerms.next(term)
    this.typeTerms.next(type)
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    ).subscribe((p) => this.products = p)

    this.typeTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((type: string) => this.productService.getProductsByType(type)),
    ).subscribe((p) => this.products = p)
  }
}