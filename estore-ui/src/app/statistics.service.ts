import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { StoreStatistics } from './StoreStatistics';
import { UserStatistics } from './UserStatistics';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  getAllUserStats(): Observable<UserStatistics[]> {
    return this.http.get<UserStatistics[]>('http://localhost:8080/stats')
      .pipe(
        tap(_ => this.log('fetched UserStats')),
        catchError(this.handleError<UserStatistics[]>('getAllUserStats', []))
      );
  }

  getStoreStats(): Observable<StoreStatistics> {
    return this.http.get<StoreStatistics>('http://localhost:8080/stats/store')
      .pipe(
        tap(_ => this.log('fetched UserStats')),
        catchError(this.handleError<StoreStatistics>('getStoreStats', undefined))
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
