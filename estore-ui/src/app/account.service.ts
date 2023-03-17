import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Token } from './token';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private accountUrl = 'http://localhost:8080/account';  // URL to web api
  private cartUrl = 'http://localhost:8080/cart'

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

  static setToken(value : number){
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
