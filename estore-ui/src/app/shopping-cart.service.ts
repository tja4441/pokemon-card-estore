import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { Product } from './product';
import { ShoppingCart } from './ShoppingCart';

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

  getCart(id: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}`;
    return this.http.get<ShoppingCart>(url).pipe(
      tap(_ => this.log(`fetched cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`getShoppingCart id=${id}`))
    );
  }

  deleteCart(id: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${id}`;
    return this.http.delete<ShoppingCart>(url).pipe(
      tap(_ => this.log(`fetched cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>(`deleteShoppingCart id=${id}`))
    );
  }

  updateCart(ShoppingCart: ShoppingCart): Observable<ShoppingCart> {

  }

  createCart(id: number): Observable<ShoppingCart> {

  }

  addToCart(id: number, product: Product): Observable<ShoppingCart> {

  }

  deleteFromCart(id: number, product: Product): Observable<ShoppingCart> {

  }

  refreshCart(id: number): Observable<ShoppingCart> {

  }

  checkout(id: number): Observable<ShoppingCart> {

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
