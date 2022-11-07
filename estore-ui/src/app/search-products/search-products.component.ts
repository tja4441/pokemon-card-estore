import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, filter, Subject, switchMap } from 'rxjs';
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
    if (!term && this.typeListSearch.length == 0) this.empty = true
    else this.empty = false
    let typeProducts: Product[] = this.searchByTypes()
    this.productService.getProductsByString(term).subscribe(p => this.products = p.filter(val => typeProducts.includes(val)))
  }

  searchByTypes(): Product[] {
    let filteredProducts: Product[] = []
    if(this.typeListSearch.length == 0) {
      this.productService.getProducts().subscribe(p => filteredProducts = p)
    }
    else {
      for(let type in this.typeListSearch) {
        this.productService.getProductsByType(type).subscribe(p => filteredProducts = filteredProducts.concat(p))
      }
      filteredProducts = [...new Set(filteredProducts)]
    }
    return filteredProducts
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      //time to wait for another input before triggering
      debounceTime(300),

      distinctUntilChanged(),

      switchMap((term: string) => this.productService.getProductsByString(term)),
    ).subscribe((p) => this.products = this.products.filter(val => this.searchByTypes().includes(val)))
  }
}