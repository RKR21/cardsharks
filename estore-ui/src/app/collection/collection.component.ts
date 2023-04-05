import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { CollectionService } from '../collection.service';
import { MessageService } from '../message.service';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent implements OnInit {
  collectionItems: Product[] = [];

  constructor (private collectionService: CollectionService) {}

  ngOnInit(): void {
    this.collectionService.getCollection().subscribe((collectionItems: Product []) => {
      this.collectionItems = collectionItems;
    });
  }

  getCollection(): void {
    this.collectionService.getCollection()
      .subscribe(collectionItems => this.collectionItems = collectionItems);
  }
}