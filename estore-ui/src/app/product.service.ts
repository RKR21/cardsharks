import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, switchMap } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productsUrl = 'http://localhost:8080/products'

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]>{
    console.log("It is being called")
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

    const url = `${this.productsUrl}/search?name=${term}`;
    
    
    
    return this.http.get<Product[]>(url).pipe(
      //console.log("products length =" + products.length)
      map(products => {
        console.log("products length =" + products.length)
        if (products.length === 0) {
          console.log("products length =" + products.length)
          return [];
        } else {
          console.log("products length =" + products.length)
          return products;
        }
      }),
      catchError(error => {
        console.log("Error occurred:", error);
        return of([]);
      })
    );
    
    
  }
  
  
  
}
