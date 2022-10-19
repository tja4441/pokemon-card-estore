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
  private productService: ProductService;
  searchForList: Observable<Product[]> | undefined;
  private searchTerms = new Subject<string>();
  name: string;

  constructor(productService: ProductService) {
    this.productService = productService;
    this.name = ""
  }

  getProductsByName(name: string): void {
    this.name = name;
    this.searchTerms.next(name);
  }

  reload(): void {
    window.location.reload;
  }

  getProductsAndReload(name: string): void {
    this.getProductsByName(name);
    this.reload();
  }

  ngOnInit(): void {
    if(this.name){
      this.searchForList = this.searchTerms.pipe(
        // wait 300ms after each keystroke before considering the term
        debounceTime(300),
  
        // ignore new term if same as previous term
        distinctUntilChanged(),
  
        // switch to new search observable each time the term changes
        switchMap((name: string) => this.productService.getProductsByString(name)),
      );
    }
  }

}
