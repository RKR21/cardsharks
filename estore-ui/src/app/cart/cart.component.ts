import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: Product[] = [];

  constructor (private cartService: CartService) {}

  ngOnInit(): void {
    this.getItems();
  }

  getItems(): void {
    this.cartService.getCart()
      .subscribe(cartItems => this.cartItems = cartItems);
  }

  add (product: Product): void {
    this.cartService.addToCart(product as Product)
      .subscribe(product => {
        this.cartItems.push(product)
      })
  }

  delete (product: Product) : void {
    this.cartItems = this.cartItems.filter (p => p !== product)
  }
}
