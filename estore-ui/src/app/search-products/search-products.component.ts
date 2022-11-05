import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, Subject, switchMap } from 'rxjs';
import { Product, CardType, CardTypeConverter} from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  private searchTerms = new Subject<string>();
  public typeDict: any = {[CardType.DARK] : false,
                          [CardType.DRAGON] : false,
                          [CardType.ELECTRIC] : false,
                          [CardType.FAIRY] : false,
                          [CardType.FIGHTING] : false,
                          [CardType.FIRE] : false,
                          [CardType.GRASS] : false,
                          [CardType.NORMAL] : false,
                          [CardType.PSYCHIC] : false,
                          [CardType.STEEL] : false,
                          [CardType.WATER] : false,
                          [CardType.TRAINER] : false}
  public empty = true
  constructor(private productService: ProductService) {}

  flipBool(type: string, term: string) {
    this.typeDict[type] = !this.typeDict[type]
    this.search(term)
  }

  search(term: string): void {
    term = term.trim()
    if (!term) this.empty = true
    else this.empty = false
    this.searchTerms.next(term)
  }

  filterByTypes(products: Product[]): Product[] {
    for(let key in this.typeDict) {
      if(this.typeDict[key]) {
        products = products.filter(val => val.types.includes(CardTypeConverter.stringToType(key)))
      }
    }
    return products
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    ).subscribe((p) => this.products = this.filterByTypes(p))
  }
}