import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  public uploadImage(file: File) {
    let formParams = new FormData();
    formParams.append('file', file);
    return this.http.post('http://localhost:8080/products/image', formParams).pipe(
      tap(_ => this.log(`added image from file=${file}`)),
      catchError(this.handleError<File>(`uploadImage file=${File}`))
    );
  }

  public getImageSrc(name: String): Observable<String> {
    let src = this.http.get('http://localhost:8080/products/' + name + '/image', {responseType: 'text'}).pipe(
      tap(_ => this.log(`fetched image id=${name}`)),
      catchError(this.handleError<String>(`getImageSrc id=${name}`))
    );
    return src;
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