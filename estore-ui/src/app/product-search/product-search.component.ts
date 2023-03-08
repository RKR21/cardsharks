import { Component, OnInit } from '@angular/core';

import { Observable, of, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Product } from '../product';
import { ProductService } from '../product.service';
@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit{
  products$: Product[] = [];
  private searchTerms = new Subject<string>();
  private previousTerm = '';

  constructor(private productService: ProductService){}

  // push a term onto the observable stream
  search(term:string): void {
    //console.log('search term:', term);
    this.searchTerms.next(term)
  }
  

  ngOnInit(): void {
    this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.searchProducts(term))
    )
    .subscribe(products => this.products$ = products);
  }
  



}
