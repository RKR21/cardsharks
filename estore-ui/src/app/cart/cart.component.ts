import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  products: Product[] = [];

  constructor (private productService: ProductService) {}

  add (id: number): void {
    
  }

  delete (product: Product) : void {
    this.products = this.products.filter (p => p !== product)
  }
}
