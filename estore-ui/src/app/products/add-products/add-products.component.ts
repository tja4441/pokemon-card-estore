import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product, CardType } from '../../model/product'
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {

  image!: File 

  products: Product[] = [];
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
  public typeListAdd: CardType[] = []

  constructor(private productService: ProductService,
              private imageService: ImageService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  flipBool(typeString: keyof typeof CardType) {
    let type: CardType = CardType[typeString]
    this.typeDict[typeString] = !this.typeDict[typeString]
    if(this.typeDict[typeString]) this.typeListAdd.push(type)
    else this.typeListAdd.splice(this.typeListAdd.indexOf(type), 1)
  }

  handleClick(type: keyof typeof CardType) {
    if(this.typeDict[CardType[type]] || this.typeListAdd.length < 2) {
      this.flipBool(CardType[type]);
    }
    else {
      alert("Each Pokemon can only have 2 types")
    }
    var element = document.getElementById(type) as HTMLInputElement
    element.checked = this.typeDict[CardType[type]]
  }

  /**
   * Queries server for product list and stores in this.products
   */
  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  onFileChange(event: any): void {
    this.image = event.target.files[0];
  }
  
  uploadImage(name: string): void {
    name = name + ".png";
    if (this.image) {
      const image = new File([this.image], name, {type: this.image.type});
      this.imageService.uploadImage(image).subscribe(_ => window.location.reload())
    }
  }

  /**
   * adds a new card to the database and then changes this.products to reflect change
   * @param name name of product
   * @param types type of the product
   * @param quantity quantity of product
   * @param price price of the card
   */
  add(name: string, types: CardType[], quantity: any, price: any): void {
    if(this.typeListAdd.length == 0) {
      alert("A Pokemon Must have at least 1 type")
      return
    }else if(!this.image){
      alert("A pokemon must have an image")
      return 
    }else if(!name){
      alert("A pokemon must have a name")
      return 
    }else if(quantity == ""){
      alert("A pokemon must have a quanity")
      return 
    }else if(price == ""){
      alert("A pokemon must have a price")
      return 
    }
    //removes whitespace
    name = name.trim();
    //tries to add product to the server using productService
    let product = new Product(0, name, types, quantity, price)
    this.productService.addProduct(product)
    //productService returns product on success which is added to UI/this.products
    .subscribe(product => {this.products.push(product), this.uploadImage(name)})
    }
 
  }

  

