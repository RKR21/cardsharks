import { TokenType } from '@angular/compiler';
import { Component } from '@angular/core';
import { AccountService } from '../account.service';
import { Token } from '../token';

@Component({
  selector: 'app-log-in-out',
  templateUrl: './log-in-out.component.html',
  styleUrls: ['./log-in-out.component.css']
})
export class LogInOutComponent {
  constructor(private accountService: AccountService){}
  private userName = AccountService.getUser();
  private token!: Token;
  private tokenValue = AccountService.getToken();
  displayValue = "User: " + AccountService.getUser();
  private isLoggedIn = AccountService.isLoggedIn();
  getUser(user:string){
    if(user != ''){
      this.userName = user;
      this.displayValue = "User Name: " + this.userName;
      this.accountService.logIn(this.userName).subscribe(value => {this.token = value as Token});
    }
  }

  logIn(){
    if(!this.isLoggedIn && this.userName != ''){
      if(this.token != null)
        this.tokenValue = this.token.token;
      if(this.tokenValue != 0){
        this.displayValue = "logged in as: " + this.userName + " token: " + this.token.token;
        this.isLoggedIn = true;
        this.update();
      }
      else
        this.displayValue = "No user: " + this.userName;
    }
    else
      this.displayValue = "Already logged in";
  }
  logOut(){
    if(this.isLoggedIn){
      this.displayValue = "You are no longer logged in"
      this.tokenValue = 0;
      this.isLoggedIn = false;
      this.update();
    }
    else
      this.displayValue = "You are not logged in"
  }

  createAccount(){
    if(!this.isLoggedIn && this.userName != ''){
      if(this.accountService.createAccount(this.userName))
        this.displayValue = "login";
        //this.logIn();
      else
        this.displayValue = "failed to create account";
    }
    else
      this.displayValue = "log out to create account";
  }

  deleteAccount(){
    if(this.isLoggedIn){
      if(this.accountService.deleteAccount())
        this.logOut();
      else
        this.displayValue = "failed to create account";
    }
    else
      this.displayValue = "log in to create account";
  }

  update(){
    AccountService.setToken(this.tokenValue);
    AccountService.setUser(this.userName);
    AccountService.setLogStatus(this.isLoggedIn);
  }
}
