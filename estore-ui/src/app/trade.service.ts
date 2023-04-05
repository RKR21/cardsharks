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

  constructor(private http: HttpClient) { }


  makeOffer(trade:Trade) {
    const url = `${this.baseUrl}/offer/${AccountService.getToken()}?userName=${trade.fromUser}?otherName=${trade.toUser}`;
    return this.http.post<Trade>(url, trade).pipe();
  }

  getTrades(): Observable<Trade[]>{
    return this.http.get<Trade[]>(this.baseUrl + "/" + AccountService.getToken())
    .pipe();
  }

  declineOffer(token: Token, trade:Trade): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer-reject/${token.token}?userName=${trade.fromUser}&otherName=${trade.toUser}`;

    
    return this.http.put<Trade>(url, trade);
  }

  acceptOffer(token: Token, trade:Trade): 
  Observable<Trade>{
    const url = `${this.baseUrl}/offer-accept/${token.token}?userName=${trade.fromUser}&otherName=${trade.toUser}`;

    
    return this.http.put<Trade>(url, trade);
  }

  getTrade(token: Token): Observable<Trade>{
    const url = `${this.baseUrl}/offer/${token.token}`;

    return this.http.get<Trade>(url);
    
  }
}
