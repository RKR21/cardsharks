import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Token } from './token';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private rooturl = 'http://localhost:8080/';
  private accountUrl = this.rooturl + 'account';
  private cartUrl = this.rooturl + 'cart';
  private collectionUrl = this.rooturl + 'collection';

  private static token = 0;
  private static userName = '';
  private static logStatus = false;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  logIn(user:string){
    const url = `${this.accountUrl}/?userName=${user}`;
    return this.http.get<Token>(url);
  }

  createAccount(user: string) {
    const urlA = `${this.accountUrl}/?userName=${user}`;
    const urlB = `${this.cartUrl}/?userName=${user}`;
    const urlC = `${this.collectionUrl}/?userName=${user}`;
    this.http.post(urlA, null);
    this.http.post(urlB, null);
    this.http.post(urlC, null);
    return true;
  }

  deleteAccount() : boolean {
    const urlA = `${this.accountUrl}/{${AccountService.getToken}}?userName=${AccountService.getUser}`;
    const urlB = `${this.cartUrl}/{${AccountService.getToken}}`;
    const urlC = `${this.collectionUrl}/{${AccountService.getToken}}`;
    this.http.delete(urlA);
    this.http.delete(urlB);
    this.http.delete(urlC);
    return true;
  }

  static setToken(value : number) {
    this.token = value;
  }
  
  static getToken() {
    return this.token;
  }

  static setUser(value : string){
    this.userName = value;
  }

  static getUser() {
    return this.userName;
  }

  static setLogStatus(value : boolean){
    this.logStatus = value;
  }

  static isLoggedIn() {
    return this.logStatus;
  }
}
