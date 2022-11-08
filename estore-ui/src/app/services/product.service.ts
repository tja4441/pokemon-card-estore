import { Injectable } from '@angular/core';
import { Product } from '../model/product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private inventoryUrl = 'http://localhost:8080/products';   // URL to web api

  httpOptions = { 
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET Product by id. Will 404 if id not found */
  getProduct(id: number): Observable<Product> {
    const url = `${this.inventoryUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /** 
   * GETs products from the inventory on our server
   * @returns returns an observable that resolves to a a product array holding all products in inventory
   */
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.inventoryUrl)
      .pipe(
        tap(_ => this.log('fetched products')),
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  /**
   * GETs products from the backend that contain a given substring
   * @param substring the substring you are looking for a card to contain in its name
   * @returns returns an observable that resolves to a a product array holding all cards that contain substring
   */
  getProductsByString(substring: String): Observable<Product[]> {
    const inventoryUrlByName = this.inventoryUrl + '/name/' + substring;
    return this.http.get<Product[]>(inventoryUrlByName)
      .pipe(
        tap(_ => this.log('fetched products')),
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  /**
   * GETs products from backend that are the given type
   * @param type the type being searched for
   * @returns an observable that resolves into a product array of cards with the given type
   */
  getProductsByType(type: String): Observable<Product[]> {
    const inventoryUrlByName = this.inventoryUrl + '/type/' + type;
    return this.http.get<Product[]>(inventoryUrlByName)
      .pipe(
        tap(_ => this.log('fetched products')),
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  //////////////////// SAVE METHODS ////////////////////

  
  /** 
   * POST: add a new product to the inventory
   * @param product the product that you would like to add to add to the database
   * @returns an observable that resolves to a product after it has been added to the database
   */
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.inventoryUrl, product, this.httpOptions).pipe(
      tap((newProduct: Product) => this.log(`added product w/ id=${newProduct.id}`)),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /**
   * PUT edits an item in the database
   * @param product an item containing the changes you would like to apply to this item
   * @returns an observable that resolves to a product after it has been added edited in the database
   */
  editProduct(product: Product): Observable<Product> {
    return this.http.put<Product>(this.inventoryUrl, product, this.httpOptions).pipe(
      tap((newProduct: Product) => this.log(`edited product w/ id=${newProduct.id}`)),
      catchError(this.handleError<Product>('editProduct'))
    );
  }

  /** 
   * DELETE: remove product from the inventory
   * @param id the id of the item that you would liek to delete from the database
   * @returns an observable that resolves to the deleted product
   */
  removeProduct(id: number): Observable<Product> {
    const url = this.inventoryUrl + '/' + id

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`removed product w/ id=${id}`)),
      catchError(this.handleError<Product>('removeProduct'))
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
