import { Component, OnInit } from '@angular/core';
import { TradeService } from '../trade.service';
import { ProductService } from '../product.service'
import { AccountService } from '../account.service';

import { Product } from '../product';
import { Trade } from '../trade';


@Component({
  selector: 'app-trade-form',
  templateUrl: './trade-form.component.html',
  styleUrls: ['./trade-form.component.css']
})
export class TradeFormComponent implements OnInit{

  productNames: string[] = [];
  userName: string = '';
  otherName: string = '';
  requestId: number = 0;
  offerId: number = 0;
  
  trades: Trade[] = [];

  constructor(private tradeService: TradeService, private productService: ProductService, private accountService: AccountService){}

  ngOnInit() {
    if(this.trades.length <= 0)
      this.getTrades();
  }

  onSubmit(fromUser: string, toUser: string, requestId: number, offerId: number) {
    fromUser = AccountService.getUser();
    this.productService.getProduct(requestId).subscribe((request: Product) => {
      this.productService.getProduct(offerId).subscribe((offer: Product) => {
        const trade: Trade = { fromUser, toUser, offer, request };
        console.log("Trade offer made: ", trade);
        this.tradeService.makeOffer(trade).subscribe();
      })
    });
  }

  onAccept() {
    this.tradeService.acceptOffer().subscribe();
  }

  onDecline() {
    this.tradeService.declineOffer().subscribe();
  }

  resolveFunction(){
    this.trades = [];
  }

  getTrades() {
    this.tradeService.getTrades()
      .subscribe(val => this.trades = val);
  }
}
