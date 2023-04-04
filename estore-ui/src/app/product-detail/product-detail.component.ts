import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../product.service';
import { CartService } from '../cart.service';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  product: Product | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }
  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }

  addToCart(): void {
    if (this.product) {
      this.cartService.addToCart(this.product).subscribe();
      this.product.quantity -= 1;
      this.productService.updateProduct(this.product);
      if (this.product.quantity == 0) {
        this.productService.deleteProduct(this.product.id);
      }
    }
  }

  isAdmin(){
    return (AccountService.getToken()==92668751);
  }

  isLoggedIn(){
    return (AccountService.isLoggedIn())
  }
}