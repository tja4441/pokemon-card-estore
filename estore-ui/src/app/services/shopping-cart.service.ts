import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { Product } from '../model/product';
import { ShoppingCart } from '../model/ShoppingCart';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  private cartUrl = 'http://localhost:8080/ShoppingCarts';   // URL to web api


  httpOptions = { 
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    
  ) { }

  /**
   * connects the ui request for seeing a cart to the ShoppingCart Controller
   * 
   * @param id The id of the cart to get
   * 
   * @returns the cart if found and no conflicts
   * 
   */
  getCart(id: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}`;
    return this.http.get<ShoppingCart>(url).pipe(
      tap(_ => this.log(`fetched cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`getShoppingCart id=${id}`))
    );
  }

  


  /**
   * connects the ui requests to delete a cart to the ShoppingCart Controller
   * 
   * @param id The id of the cart to delete
   * 
   * @returns ok if deleted
   */
  deleteCart(id: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}`;
    return this.http.delete<ShoppingCart>(url).pipe(
      tap(_ => this.log(`fetched cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`deleteShoppingCart id=${id}`))
    );
  }

  /**
   * connects the ui requests to add a product to a cart to the ShoppingCart Controller
   * 
   * @param id The id of the shopping cart being added to
   * 
   * @param product The product being added to the cart
   * 
   * @returns shopping cart that was modified and status of ok if okay
   */
  addToCart(id: number, product: Product): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}/add`;
    return this.http.put<ShoppingCart>(url, product, this.httpOptions).pipe(
      tap(_ => this.log(`added product to cart w/ id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`addToCart id=${id}`))
    );
  }

  /**
   * connects the ui requests to delete from a cart to the ShoppingCart Controller
   * 
   * @param id The id of the shopping cart being deleted from
   * 
   * @param product The product being deleted from the cart
   * 
   * @returns shoppingCart deleted from and status of ok if okay
   */
  deleteFromCart(id: number, product: Product): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}/delete`;
    return this.http.put<ShoppingCart>(url, product,this.httpOptions).pipe(
      tap(_ => this.log(`deleted product from cart w/ id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`deleteFromCart id=${id}`))
    );
  }

  /**
   * connects the ui of the shopping cart refreshing to the ShoppingCart Controller
   * 
   * @param id The id of the shopping cart needing refreshed
   * 
   * @returns shoppingCart refreshed and status of ok if okay
   */
  refreshCart(id: number): Observable<boolean> {
    const url = `${this.cartUrl}/${id}/refresh`;
    return this.http.put<boolean>(url, this.httpOptions).pipe(
      tap(_ => this.log(`refreshed cart id=${id}`)),
      catchError(this.handleError<boolean>(`refreshCart id=${id}`))
    );
  }

  /**
   * connects the checkout request ui side to the ShoppingCart Controller
   * 
   * @param id The id of the cart needing checked out
   * 
   * @returns the shoppingCart checked out and status of ok if okay
   */
  checkout(id: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}/checkout`;
    return this.http.put<ShoppingCart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`checked out cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`checkout id=${id}`))
    );
  }

  /**
   * Handle Http operation that failed and Let the app continue
   *
   * @param operation - name of the operation that failed
   * 
   * @param result - optional value to return as the observable result
   */
  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); //log to console

      this.log(`${operation} failed: ${error.message}`);

      //Let the app keep running by returning an empty result
      return of(result as T);
    };
  }

  /**
   * this is the log method that updates the message service with a message about what happened
   * 
   * @param message the message to be added to the log for the admin
   */
  log(message: string) {
    this.messageService.add(`ProductService: ${message}`);
  }

}
