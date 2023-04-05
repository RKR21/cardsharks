import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Trade } from './trade';
import { Token } from './token';
import { Product } from './product';
import { AccountService } from './account.service';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  private baseUrl = 'http://localhost:8080/collection';
  private tradesUrl = 'http://localhost:8080/trades';
  constructor(private http: HttpClient) { }


  makeOffer(token: Token, trade:Trade): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer/${token.token}?userName=${trade.fromUser}&otherName=${trade.toUser}`;

    
    return this.http.post<Trade>(url, trade);
  }
  getTrades(): Observable<Trade[]>{
    return this.http.get<Trade[]>(this.baseUrl + "/" + AccountService.getToken())
    .pipe();
  }
  declineOffer(token: number): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer-reject/${token}`;

    
    return this.http.put<Trade>(url, token);
  }
  acceptOffer(token: number): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer-accept/${token}`;

    
    return this.http.put<Trade>(url, token);
  }

  getTrade(token: number): Observable<Trade>{
    const url = `${this.baseUrl}/offer/${token}`;

    return this.http.get<Trade>(url);
    
  }
}
