import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { MessageService } from '../message.service';
import { CheckoutService } from '../checkout.service';
import { Product } from '../product';
import { Payment } from '../payment';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent {
  checkoutItems: Product [] = [];
  payments: Payment [] = [];
  
  constructor (private cartService: CartService,
  private checkoutService: CheckoutService,
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
}
