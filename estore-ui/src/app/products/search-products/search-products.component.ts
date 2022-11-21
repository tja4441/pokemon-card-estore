import { Component, OnInit, Input } from '@angular/core';
import { Product, CardType } from '../../model/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  public products: Product[] = []
  @Input() searchTerm?: string = "";
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
  constructor(private productService: ProductService) {}

  handleClick(type: keyof typeof CardType, term: string) {
    this.flipBool(CardType[type]);
    this.search(term);
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDictSearch[typeString] = !this.typeDictSearch[typeString]
    if(this.typeDictSearch[typeString]) this.typeListSearch.push(type)
    else this.typeListSearch.splice(this.typeListSearch.indexOf(type),1)
  }

  search(term: string): void {
    if (term == "") {
      this.productService.getProducts().subscribe(p => {
        this.products = p;
      })
    } else {
      this.productService.getProductsByString(term).subscribe(p => {
        this.products = p;
      });
    }
    this.products = this.filterByTypes(this.products);
  }

  filterByTypes(products: Product[]): Product[] {
    let typeProducts: Product[] = [];
    for(let key in this.typeDictSearch) {
      if(this.typeDictSearch[key]) {
        typeProducts = typeProducts.concat((products.filter(val => val.types.includes(key))), typeProducts);
      }
    }
    const filteredProducts = typeProducts.filter((value, index) => typeProducts.indexOf(value) === index);
    return filteredProducts;
  }

  ngOnInit(): void {
    if ( this.searchTerm == "" ) {
      this.search(this.searchTerm);
    } else {
      this.productService.getProducts().subscribe(p => {
        this.products = p;
      })
    }
  }
}