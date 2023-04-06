import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { CollectionService } from '../collection.service';
import { MessageService } from '../message.service';
import { Product } from '../product';
import { Payment } from '../payment';
import { AccountService } from '../account.service';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent {
  checkoutItems: Product [] = [];
  payments: Payment [] = [];
  
  constructor (private cartService: CartService,
  private collectionService: CollectionService,
  private productService: ProductService,
  private accountService: AccountService,
  private messageService: MessageService) {}

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cartItems: Product []) => {
      this.checkoutItems = cartItems;
    });
    this.accountService.getPayments().subscribe((payments: Payment []) => {
      this.payments = payments;
    });
  }

  remove(product : Product){
    this.collectionService.addToCollection(product).subscribe();
    this.cartService.removeFromCart(product).subscribe();
  }

  checkout(){
    for(let i = 0; i < this.checkoutItems.length; ++i){
      this.productService.decrementStock(this.checkoutItems[i]);
      for(let j = this.checkoutItems[i].quantity; j > 0; --j){
        this.remove(this.checkoutItems[i]);
      }
    }
  }
}
