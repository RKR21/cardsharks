import { Component, OnInit } from '@angular/core';
import { TradeService } from '../trade.service';
import { ProductService } from '../product.service'
import { AccountService } from '../account.service';

import { Product } from '../product';
import { Trade } from '../trade';
import { Token } from '../token';
import { CollectionService } from '../collection.service';


@Component({
  selector: 'app-trade-form',
  templateUrl: './trade-form.component.html',
  styleUrls: ['./trade-form.component.css']
})
export class TradeFormComponent implements OnInit{
  productNames: string[] = [];
  //token: Token = { token: 0};
  userName: string = '';
  otherName: string = '';
  requestId: number = 0;
  offerId: number = 0;
  trade!: Trade;
  
  the_trade!: Trade;
  constructor(private tradeService: TradeService, private productService: ProductService, private accountService: AccountService){}

  ngOnInit() {
    this.productService.getProducts()
      .subscribe((products: Product[]) => {
        // extract the names of the products and store them in productNames
        this.productNames = products.map(product => product.name);
      });
      this.tradeService.getTrades().subscribe((trade: Trade) => {
        this.the_trade = trade;
      });
      // const pikachu: Product = {id: 1, name: 'pikachu', price: 3,quantity:3}
      // const trade: Trade = { fromUser: "jim", toUser: "bill", offer: pikachu, request: pikachu};
      // this.the_trade = trade;
  }

  hasTrade(){
    return !this.the_trade == null;
  }
  
  onSubmit(fromUser: string, toUser: string, requestId: number, offerId: number) {
    fromUser = AccountService.getUser();
    this.productService.getProduct(requestId).subscribe((request: Product) => {
      this.productService.getProduct(offerId).subscribe((offer: Product) => {
        const trade: Trade = { fromUser, toUser, offer, request };
        console.log("Trade offer made: ", trade);
        this.tradeService.makeOffer(trade).subscribe()
      })
    });
  }

  onAccept() {
    this.tradeService.acceptOffer().subscribe();
  }

  onDecline() {
    this.tradeService.declineOffer().subscribe();
  }

  // resolve_trade() {
  //   this.the_trades = [];
  // }

//   getTrades() {
//     this.tradeService.getTrades()
//       .subscribe(trades => this.the_trades = trades.slice());
//   }
}
