import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { StoreStatistics } from '../model/StoreStatistics';
import { UserStatistics } from '../model/UserStatistics';
import { ShoppingCart } from '../model/ShoppingCart';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  //baseline url for statistics functions
  private statsUrl = 'http://localhost:8080/stats';

  httpOptions = { 
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private userService: UserService
  ) { }

  /**GETS All user Stats from the backend
   * @returns An observable that resolves to an array of UserStaistics objects
   */
  getAllUserStats(): Observable<UserStatistics[]> {
    return this.http.get<UserStatistics[]>(this.statsUrl)
      .pipe(
        tap(_ => this.log('fetched UserStats')),
        catchError(this.handleError<UserStatistics[]>('getAllUserStats', []))
      );
  }

  /**GETS the StoreStatistics Object from the Backend
   * @returns An observable that resolves to a StoreStatistics objcet
   */
  getStoreStats(): Observable<StoreStatistics> {
    const url = `${this.statsUrl}/store`
    return this.http.get<StoreStatistics>(url)
      .pipe(
        tap(_ => this.log('fetched UserStats')),
        catchError(this.handleError<StoreStatistics>('getStoreStats', undefined))
      );
  }

  /**Sends User ID and shopping cart to the backend to aggregate the data
   * 
   */
  updateUserStats(cart: ShoppingCart): Observable<UserStatistics> {
    let uID = this.userService.getId();
    const url = `${this.statsUrl}/${uID}`
    
    return this.http.put<UserStatistics>(url,cart,this.httpOptions).pipe(
      tap((newStats: UserStatistics) => this.log(`updated stats w/ id=${newStats.id}`)),
      catchError(this.handleError<UserStatistics>('updateStatistics'))
    );
  }

  /**
   * sends the user id and session time to the backend for recalculation of session time statistics
   * @param sessionTime the amount of time that a user has gone from login to checkout OR last checkout to next checkout
   * @returns the user statistic that was modified
   */
  updateUserSessionData(sessionTime: number): Observable<UserStatistics> {
    let uID = this.userService.getId();
    const url = `${this.statsUrl}/sessionData/${uID}`

    return this.http.put<UserStatistics>(url, sessionTime, this.httpOptions).pipe(
      tap((newStats: UserStatistics) => this.log(`updated session data for user w/ id=${newStats.id}`)),
      catchError(this.handleError<UserStatistics>('updateSessionData'))
    );
  }

  /**Sends shopping cart to the backend to aggregate the data for the store statistics
   * 
   */
   updateStoreStats(cart: ShoppingCart): Observable<StoreStatistics> {
    const url = `${this.statsUrl}/store`
    return this.http.put<StoreStatistics>(url, cart, this.httpOptions).pipe(
      tap((newStoreStats: StoreStatistics) => this.log(`updated store statistics`)),
      catchError(this.handleError<StoreStatistics>('updateStoreStatistics'))
    );
  }

  /**Sends sessionTime to the backend to aggregate the data for the store statistics
   * 
   */
   updateStoreSessionData(sessionTime: number): Observable<StoreStatistics> {
    const url = `${this.statsUrl}/store/sessionData/${sessionTime}`
    return this.http.put<StoreStatistics>(url, this.httpOptions).pipe(
      tap((newStoreStats: StoreStatistics) => this.log(`updated store statistics w/ Average Time=${newStoreStats.avgSessionTime}`)),
      catchError(this.handleError<StoreStatistics>('updateStoreSessionData'))
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
