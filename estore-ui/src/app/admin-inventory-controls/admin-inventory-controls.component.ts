import { Component } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';
@Component({
  selector: 'app-admin-inventory-controls',
  templateUrl: './admin-inventory-controls.component.html',
  styleUrls: ['./admin-inventory-controls.component.css']
})
export class AdminInventoryControlsComponent {
  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(hero: Product): void {
    this.products = this.products.filter(h => h !== hero);
    this.productService.deleteProduct(hero.id).subscribe();
  }
}
