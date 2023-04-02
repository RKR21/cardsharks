import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Trade } from './trade';
import { Token } from './token';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  private baseUrl = 'http://localhost:8080/trades';
  constructor(private http: HttpClient) { }


  makeOffer(token: Token, userName: string, otherName: string, request: Product, offer: Product): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer/${token.token}?userName=${userName}&otherName=${otherName}`;
    const info = {request: request, offer: offer};
    return this.http.post<Trade>(url, info);
  }



  
}
