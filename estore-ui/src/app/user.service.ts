import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { MessageService } from './message.service';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user';
import {CookieService} from 'ngx-cookie';

@Injectable({
  providedIn: 'root'
})
//id = -1, username = "", and shopping cart is an empty array if user is logged out
export class UserService {
  private id: number = -1

  public username: string = ""

  //baseline url for user functions
  private userUrl = 'http://localhost:8080/user';

  //neccessary httpOptions for posting a json object
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private cookieSerice: CookieService) { 
    }
  
  //NOTE: is not able to tell when it gets different http for a Conflict
  /**
   * Tries to register a user to the database and catches an error if
   * the server returns a bad http response
   * @param user The user that you would like to register to the database
   * @returns An observable that resolves to the user after it 
   */
  register(user: User): Observable<User> {
    return this.http.post<User>(this.userUrl, user, this.httpOptions)
    .pipe(
      tap(_ => this.log('grabbed user')),
      catchError(this.handleError<User>('register'))
    );
  }
  
  //NOTE: is not able to tell when it gets different http for a Conflict
   /**
   * Tries to log in a user to the database and catches an error if
   * the server returns a bad http response
   * @param username The username that you would like to register to the database
   * @returns An observable that resolves to the user after it 
   */
  login(username: string): Observable<User> {
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
  
  /**
   * sets the global user to the passed in user
   * @param user the "global" user
   */
  setUser(user: User): void{
    if(user){
      this.id = user.id
      this.username = user.UserName
      this.setCookie()
    }
  }

  /**
   * @returns returns the "global" user
   */
  getUser(): User {
    return {id: this.id, UserName: this.username}
  }

  getId(): number {
    return this.id;
  }


  /**
   * @returns returns true if there is a logged in "global" user
   */
  isLoggedIn(): boolean {
    return this.id != -1 && this.username != ""
  }

  /**
   * @returns returns true if the global user is logged in as "admin"
   */
  isAdmin(): boolean {
    return this.id == 0 && this.username == "admin";
   }
  
  /**
  * logs out "global" user
  */
  logout(): void {
    this.id = -1;
    this.username = ""
    this.setCookie()
  }

  setCookie(){
    this.cookieSerice.put('username', this.username)
    this.cookieSerice.put('id', String(this.id))
  }
  
  getCookie(){
    let user  = this.cookieSerice.get('username')
    if(user){
      this.username = user;
    }
    this.id = Number(this.cookieSerice.get('id'))
  }
}
