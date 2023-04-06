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
    const url = `${this.baseUrl}/offer/${AccountService.getToken()}`;
    return this.http.post<Trade>(url, trade).pipe();
  }

  getTrades() {
    return this.http.get<Trade>(this.baseUrl + "/" + AccountService.getToken())
    .pipe();
  }

  declineOffer() {
    const url = `${this.baseUrl}/offer-reject/${AccountService.getToken()}`;
    return this.http.get<Trade>(url).pipe();
  }

  acceptOffer() {
    const url = `${this.baseUrl}/offer-accept/${AccountService.getToken()}`;
    return this.http.get<Trade>(url).pipe();
  }
}
