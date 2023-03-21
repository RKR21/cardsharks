import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, switchMap } from 'rxjs';
import { tap } from 'rxjs/operators';

import { Product } from './product';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productsUrl = 'http://localhost:8080/dashboard'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(private http: HttpClient,

    private messageService: MessageService) { }

  getProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.productsUrl)
    .pipe(
      )
    ;
  }

  getProduct(id: number): Observable<Product>{
    
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Product>(url);
  }

  searchProducts(term: string): Observable<Product[]> {
    
    if (!term.trim()) {
      return of([]);
    }

    const url = `${this.productsUrl}/search?name=${term}`;
    
    
    
    return this.http.get<Product[]>(url).pipe(
      map(products => {
        if (products.length === 0) {
          return [];
        } else {
          return products;
        }
      }),
      catchError(error => {
        console.log("Error occurred:", error);
        return of([]);
      })
    );
    
    
  }
  addProduct(hero: Product): Observable<Product> {
    return this.http.post<Product>(this.productsUrl, hero, this.httpOptions).pipe(
      tap((newHero: Product) => this.log(`added hero w/ id=${newHero.id}`)),
      catchError(this.handleError<Product>('addHero'))
    );
  }

  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted hero id=${id}`)),
      catchError(this.handleError<Product>('deleteHero'))
    );
  }

  updateProduct(hero: Product): Observable<any> {
    return this.http.put(this.productsUrl, hero, this.httpOptions).pipe(
      tap(_ => this.log(`updated hero id=${hero.id}`)),
      catchError(this.handleError<any>('updateHero'))
    );
  }



  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`HeroService: ${message}`);
  }

}
