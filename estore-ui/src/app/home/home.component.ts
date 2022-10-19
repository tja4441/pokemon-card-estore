import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private productService: ProductService;
  private userService: UserService;

  constructor(productService: ProductService, userService: UserService) {
    this.productService = productService;
    this.userService = userService;
  }

  getProducts(): Observable<Product[]> {
    return this.productService.getProducts();
  }

  isLoggedIn(): boolean {
    return this.userService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.userService.isLoggedIn() && this.userService.isAdmin();
  }

  ngOnInit(): void {
  }

}
