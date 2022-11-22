import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { MessageService } from './message.service';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../model/user';
import { PassUser } from '../model/passUser';
import { ChangePass } from '../model/changePass';

@Injectable({
  providedIn: 'root'
})
//id = 0, username = "", and shopping cart is an empty array if user is logged out
export class UserService {
  private id: number = 0
  public username: string = ""
  private loginTime: Date | undefined;

  //baseline url for user functions
  private userUrl = 'http://localhost:8080/user';

  //neccessary httpOptions for posting a json object
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService) { 
    }
  
  //NOTE: is not able to tell when it gets different http for a Conflict
  /**
   * Tries to register a user to the database and catches an error if
   * the server returns a bad http response
   * @param user The user that you would like to register to the database
   * @returns An observable that resolves to the user after it 
   */
  public register(user: PassUser): Observable<User> {
    return this.http.post<User>(this.userUrl, user, this.httpOptions)
    .pipe(
      tap(_ => this.log('registering user...')),
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
  public login(username: string, password: string): Observable<User> {
    const url = `${this.userUrl}/${username}/${password}`;
    return this.http.get<User>(url)
      .pipe(
        tap(_ => this.log('grabbing user...')),
        catchError(this.handleError<User>('login'))
      );
  }

  /**
  * Attempts to change password of this user using information from a
  * change of password form
  */
  public changePass(changePass: ChangePass): Observable<boolean> {
    const url = `${this.userUrl}/password/${this.id}`
    return this.http.put<boolean>(url, changePass, this.httpOptions)
      .pipe(
        tap(_ => this.log(`attempting to change password from ${changePass.oldPass} to ${changePass.newPass}`)),
        catchError(this.handleError<boolean>('change password'))
      )
  }

  public createAdmin(user: PassUser): Observable<User> {
    const url = this.userUrl + "/createAdmin"
    return this.http.post<User>(url, user, this.httpOptions)
    .pipe(
      tap(_ => this.log('creating admin...')),
      catchError(this.handleError<User>('create admin'))
    );
  }

  public deleteAccount(): Observable<boolean> {
    const url = `${this.userUrl}/${this.id}`
    return this.http.delete<boolean>(url)
    .pipe(
      tap(_ => this.log("deleting account...")),
      catchError(this.handleError<boolean>('delete account'))
    )
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
   * records the log in time
   */
  setLoginTime() {
    if(this.id > 0) this.loginTime = new Date();
  }

  /**
   * @returns returns session time in minutes
   */
  getSessionTime(): number {
    if(!this.loginTime) return 0
    //@ts-ignore
    else return (new Date() - this.loginTime) / 1000 / 60
  }

  /**
   * sets the global user to the passed in user
   * @param user the "global" user
   */
  setUser(user: User): void{
    if(user){
      this.id = user.id
      this.username = user.UserName
      this.savState()
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
    return this.id != 0 && this.username != ""
  }

  /**
   * @returns returns true if the global user is logged in as "admin"
   */
  isAdmin(): boolean {
    return this.id < 0;
   }
  
  /**
  * logs out "global" user
  */
  logout(): void {
    this.id = 0;
    this.username = ""
    this.loginTime = undefined;
    this.savState()
  }

  savState(){
    sessionStorage.setItem('id',String(this.id))
    sessionStorage.setItem('userName',this.username)
  }
  
  getState(){
    const valueId = sessionStorage.getItem('id')
    if(valueId){
      this.id = Number(valueId)
    }
    const value = sessionStorage.getItem('userName')
    if(value){
      this.username = String(value)
    }
  }
}
