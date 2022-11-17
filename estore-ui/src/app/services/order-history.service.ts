import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { OrderHistory } from '../model/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {
  private historyURL = 'http://localhost:8080/ShoppingCarts/history';   // URL to web api

  httpOptions = { 
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /**
  * GETS all orders from the API
  * 
  * @returns an Observable that resolves into an array of OrderHistory
  */
  getAllOrders(): Observable<OrderHistory[]> {
    return this.http.get<OrderHistory[]>(this.historyURL).pipe(
      tap(_ => this.log('fetched orders')),
      catchError(this.handleError<OrderHistory[]>('getAllOrders', []))
    );
  }

  /**
  * GETS All past Orders made By the User
  * 
  * @param id a number reprsenting the id of the user we want to find the orders of
  * 
  * @returns an Observable that resolves into an array of OrderHistory
  */
  getOrdersByUserID(id: number): Observable<OrderHistory[]> {
    const url = `${this.historyURL}/${id}`;
    return this.http.get<OrderHistory[]>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<OrderHistory[]>(`getProduct id=${id}`))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console

      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ProductService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ProductService: ${message}`);
  }
}