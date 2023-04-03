import { Component, OnInit } from '@angular/core';
import { TradeService } from '../trade.service';
import { ProductService } from '../product.service'
import { AccountService } from '../account.service';

import { Product } from '../product';
import { Trade } from '../trade';
import { Token } from '../token';


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

  constructor(private tradeService: TradeService, private productService: ProductService, private accountService: AccountService){}

  ngOnInit() {
    this.productService.getProducts()
      .subscribe((products: Product[]) => {
        // extract the names of the products and store them in productNames
        this.productNames = products.map(product => product.name);
      });
  }
  


  onSubmit(fromUser: string, toUser: string, requestId: number, offerId: number) {
    const tokenValue = AccountService.getToken();
    const token: Token = { token: tokenValue };

    this.productService.getProduct(requestId).subscribe((request: Product) => {
      this.productService.getProduct(offerId).subscribe((offer: Product) => {
        const trade: Trade = { fromUser, toUser, request, offer };
        console.log("Trade offer made: ", trade);
        this.tradeService.makeOffer(token, trade)
          .subscribe((trade: Trade) => {
            console.log("HEY");
            this.trade = trade;
            console.log("THERE")
            console.log("Trade offer made: ", trade);
          }, (error) => {
            console.error("Error offering trade: ", error);
            console.log(error);
          });
      })
    })
  }

  
  
}
