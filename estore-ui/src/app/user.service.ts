import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { MessageService } from './message.service';
import { Product } from './product';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private id: number = -1

  private username: string = ""

  private shoppingCart: Product[] = [];

  private userUrl = 'http://localhost:8080/home';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }
  
  //NOTE: is not able to tell when it gets different http for a Conflict
  register(user: User) {
    return this.http.post<User>(this.userUrl, user, this.httpOptions)
    .pipe(
      tap(_ => this.log('grabbed user')),
      catchError(this.handleError<User>('register'))
    );
  }
  
  //NOTE: is not able to tell when it gets different http for a Conflict
  login(username: string){
    const url = `${this.userUrl}/${username}`;
    return this.http.get<User>(url)
      .pipe(
        tap(_ => this.log('grabbed user')),
        catchError(this.handleError<User>('login'))
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
    this.messageService.add(`UserService: ${message}`);
  }
  
  setUser(user: User): void{
    if(user){
      this.id = user.id
      this.username = user.UserName
    }
  }

  getUser(): User{
    return {id: this.id, UserName: this.username}
  }

  isLoggedIn(): boolean {
    return this.id != -1 && this.username != ""
  }

  isAdmin(): boolean {
    return this.id == 0 && this.username == "admin";
  }

}
