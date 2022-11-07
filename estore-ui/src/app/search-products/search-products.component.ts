import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, Subject, switchMap } from 'rxjs';
import { CardType, Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  private searchTerms = new Subject<string>();
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
    if((this.typeListSearch.length < 2) || this.typeDictSearch[type]) {
        this.flipBool(CardType[type])
        this.search(term)
    }
    let checkBox = document.getElementById(type) as HTMLInputElement
    if(checkBox) checkBox.checked = this.typeDictSearch[type]
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDictSearch[typeString] = !this.typeDictSearch[typeString]
    if(this.typeDictSearch[typeString]) this.typeListSearch.push(type)
    else this.typeListSearch.splice(this.typeListSearch.indexOf(type), 1)
  }

  search(term: string): void {
    term = term.trim()
    if (!term && this.typeListSearch.length == 0) this.empty = true
    else this.empty = false
    this.searchTerms.next(term)
  }

  filterByTypes(filteredProducts: Product[]): Product[] {
    for(let type in this.typeListSearch) {
      let productsOfType: Product[]
      this.productService.getProductsByType(type).subscribe(p => productsOfType = p)
      filteredProducts = filteredProducts.filter(val => productsOfType.includes(val))
    }
    return filteredProducts
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    ).subscribe((p) => this.products = this.products.filter(val => this.filterByTypes(p).includes(val)))
  }
}