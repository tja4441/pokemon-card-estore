import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, filter, Observable, Subject, switchMap } from 'rxjs';
import { CardType, Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  private searchTerms = new Subject<string>()
  public typeDictSearch: any = {"DARK" : false,
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
  public typeListSearch: CardType[] = []
  public empty = true
  constructor(private productService: ProductService) {}

  handleClick(type: keyof typeof CardType, term: string) {
    this.flipBool(CardType[type])
    this.search(term)
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDictSearch[typeString] = !this.typeDictSearch[typeString]
    if(this.typeDictSearch[typeString]) this.typeListSearch.push(type)
    else this.typeListSearch.splice(this.typeListSearch.indexOf(type), 1)
  }

  search(term: string): void {
    term = term.trim()
    this.empty = true;
    if( !term ) {
      for(let key in this.typeDictSearch){
        if ( this.typeDictSearch[key] ){
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
    for(let key in this.typeDictSearch) {
      if(this.typeDictSearch[key]) {
        products = products.concat((products.filter(val => val.types.includes(key))));
      }
    }
    return products.filter((value, index) => products.indexOf(value) === index);
  }

  ngOnInit(): void {
  }
}