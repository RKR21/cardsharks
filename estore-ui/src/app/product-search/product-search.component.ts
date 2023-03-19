import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs';

import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit {
  public term: string = '';
  products: Product[] = [];

  constructor(private productService: ProductService){}

  search(term:string): void {
    this.term = term.trim();
    this.productService.searchProducts(term).subscribe(products => {
      this.products = products;
    });
  }

  ngOnInit(): void {
    
  }
}
