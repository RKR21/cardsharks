import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Token } from './token';
import { Payment } from './payment';

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
    const url = `${this.accountUrl}?userName=${user}`;
    return this.http.get<Token>(url).pipe();
  }

  createAccount(user: string) {
    const urlA = `${this.accountUrl}/?userName=${user}`;
    const urlB = `${this.cartUrl}/?userName=${user}`;
    const urlC = `${this.collectionUrl}/?userName=${user}`;
    this.http.post(urlA, null).subscribe();
    this.http.post(urlB, null).subscribe();
    this.http.post(urlC, null).subscribe();
    return true;
  }

  deleteAccount() : boolean {
    const urlA = `${this.accountUrl}/${AccountService.getToken()}?userName=${AccountService.getUser()}`;
    const urlB = `${this.cartUrl}/${AccountService.getToken()}`;
    const urlC = `${this.collectionUrl}/${AccountService.getToken()}`;
    this.http.delete(urlA).subscribe();
    this.http.delete(urlB).subscribe();
    this.http.delete(urlC).subscribe();
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

  addPayment(payment:Payment){
    const urlA = `${this.accountUrl}/${AccountService.getToken()}/payment/?userName=${AccountService.getUser()}`;
    this.http.post<Payment>(urlA, payment, this.httpOptions).subscribe();
  }

  getPayments(){
    const urlA = `${this.accountUrl}/${AccountService.getToken()}/payment/?userName=${AccountService.getUser()}`;
    return this.http.get<Payment[]>(urlA, this.httpOptions).pipe();
  }
  
  deletePayment(payment:Payment){
    const urlA = `${this.accountUrl}/${AccountService.getToken()}/payment/?userName=${AccountService.getUser()}`;
    this.http.put<Payment>(urlA, payment, this.httpOptions).subscribe();
  }
}
