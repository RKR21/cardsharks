import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { AccountService } from './account.service';
import { MessageService } from './message.service';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/cart'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) {}

  getCart(): Observable<Product[]> {
    return this.http.get<Product[]>(this.cartUrl + "/" + AccountService.getToken())
    .pipe(
      tap(_ => this.log('fetched cart items')),
      catchError(this.handleError<Product[]>('getCart', [])));
  }

  addToCart(product: Product): Observable<Product> {
    const url = `${this.cartUrl}/${AccountService.getToken()}`;
    return this.http.put<Product>(url, product, this.httpOptions).pipe(
      tap((newProd: Product) => this.log(`added product w/ id=${newProd.id}`)),
      catchError(this.handleError<Product>('addProd'))
    );
  }

  removeFromCart (product: Product) {
    const url = `${this.cartUrl}/${AccountService.getToken()}/${product.id}`;
    return this.http.delete<Product>(url, this.httpOptions).subscribe();
  }

  /** Log a CartService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CartService: ${message}`);
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

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
