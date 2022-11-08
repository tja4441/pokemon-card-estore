import { Component, Input, OnInit } from '@angular/core';
import { CardType, Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  @Input() searchTerm!: string;
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
    this.flipBool(CardType[type]);
    this.search(term, this.products);
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDictSearch[typeString] = !this.typeDictSearch[typeString]
    if(this.typeDictSearch[typeString]) this.typeListSearch.push(type)
    else this.typeListSearch.splice(this.typeListSearch.indexOf(type), 1)
  }

  search(term: string, products: Product[]): void {
    this.productService.getProductsByString(term).subscribe(p => {
      products = p;
      return this.filterByTypes(products)
    });
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
    this.search
  }
}