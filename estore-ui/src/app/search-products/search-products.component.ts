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
  public typeDict: any = {"DARK" : false,
                           "DRAGON" : false,
                           "ELECTRIC" : false,
                           "FAIRY" : false,
                           "FIGHTING" : false,
                           "FIRE" : false,
                           "GRASS" : false,
                           "NORMAL" : false,
                           "PSYCHIC" : false,
                           "STEEL" : false,
                           "WATER" : false,
                           "TRAINER" : false}
  public empty = true
  constructor(private productService: ProductService) {}

  flipBool(type: string) {
    this.typeDict[type] = !this.typeDict[type]
    
  }

  search(term: string): void {
    let typeSelected: boolean = false
    typeSelected = false
    for(let key in this.typeDict) {
      if(this.typeDict[key]) {
        typeSelected = true
        this.products = this.products.filter(val => val.types.includes(key))
      }
    }

    term = term.trim()
    if (!term && !typeSelected) this.empty = true
    else this.empty = false
    this.searchTerms.next(term)
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    ).subscribe((p) => this.products = this.products.filter(val => p.includes(val)))
  }
}