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

  flipBool(type: string, term: string) {
    this.typeDict[type] = !this.typeDict[type]
    this.search(term)
  }

  search(term: string): void {
    term = term.trim()
    this.empty = true;
    if( !term ) {
      for(let key in this.typeDict){
        if ( this.typeDict[key] ){
          this.empty = false;
          break;
        }
      }
    } else {
      this.empty = false;
    }
    
    this.searchTerms.next(term)
  }

  filterByTypes(products: Product[]): Product[] {
    for(let key in this.typeDict) {
      if(this.typeDict[key]) {
        products = products.concat((products.filter(val => val.types.includes(key))));
      }
    }
    return products.filter((value, index) => products.indexOf(value) === index);
  }

  ngOnInit(): void {
  }
}