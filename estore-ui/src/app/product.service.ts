import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productsUrl = 'api/heroes'

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.productsUrl);
  }

  getProduct(id: number): Observable<Product>{
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Product>(url);
  }

  searchProducts(term: string): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    //const url = `${this.productsUrl}/search?name=${term}`;
    
    //const url = `${this.productsUrl}/search?name=${term}&t=${new Date().getTime()}`;
    const url = `${this.productsUrl}/search?name=${term}`;
    return this.http.get<Product[]>(url);
  }
}
