import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/cart'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  getCart(): Observable<Product[]> {
    return this.http.get<Product[]>(this.cartUrl);
  }

  addToCart(product: Product): Observable<Product> {
    return this.http.post<Product>(this.cartUrl, product, this.httpOptions);
  }

  removeFromCart (id: number): Observable<Product> {
    const url = `${this.cartUrl}/${id}`;

    return this.http.delete<Product>(url, this.httpOptions);
  }
}
