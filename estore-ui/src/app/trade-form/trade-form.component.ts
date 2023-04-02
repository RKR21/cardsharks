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
  request: Product = {id: 0, name: '', price: 0, quantity: 0};
  offer: Product = {id: 0, name: '', price: 0, quantity: 0};
  trade!: Trade;

  constructor(private tradeService: TradeService, private productService: ProductService, private accountService: AccountService){}

  ngOnInit() {
    this.productService.getProducts()
      .subscribe((products: Product[]) => {
        // extract the names of the products and store them in productNames
        this.productNames = products.map(product => product.name);
      });
  }



  onSubmit(userName: string, otherName: string, request: Product, offer: Product){
    const tokenValue = AccountService.getToken();
    const token: Token = { token: tokenValue};
    this.tradeService.makeOffer(token, userName, otherName, request, offer)
    .subscribe((trade: Trade) => {
       this.trade = trade;
       console.log("Trade offer made: ", trade);
    }, (error) => {
      console.error("Error offering trade: ", error);
    
  });
}
}
