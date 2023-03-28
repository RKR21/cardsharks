import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { CartService } from '../cart.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: Product[] = [];
  displayValue = "Please work";

  constructor (private cartService: CartService,
    private messageService: MessageService) {}

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cartItems: Product []) => {
      this.cartItems = cartItems;
    });
  }

  getCart(): void {
    this.cartService.getCart()
      .subscribe(cartItems => this.cartItems = cartItems);
  }

  add (product: Product): void {
    this.cartService.addToCart(product)
      .subscribe(product => {
        this.cartItems.push(product)
      })
  }

  remove (product: Product) : void {
    this.displayValue = "You better work";
    // console.log("we are here");
    // this.cartItems = this.cartItems.filter(p => p !== product);
    this.cartService.removeFromCart(product);
  }
}
