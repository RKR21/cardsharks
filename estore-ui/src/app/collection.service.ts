import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { AccountService } from './account.service';
import { MessageService } from './message.service';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private collectionUrl = 'http://localhost:8080/collection'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) {}

  getCollection(): Observable<Product[]> {
    return this.http.get<Product[]>(this.collectionUrl + "/" + AccountService.getToken())
    .pipe();
  }

  addToCollection(product : Product){
    const url = `${this.collectionUrl}/${AccountService.getToken()}`;
    return this.http.put<Product>(url, product, this.httpOptions).pipe();
  }

}
