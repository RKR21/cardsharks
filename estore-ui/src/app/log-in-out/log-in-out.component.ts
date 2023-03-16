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
  userName = '';
  token!: Token;
  tokenValue = 0;
  displayValue = "You are not logged in"
  isLoggedIn = false;
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
      if(this.tokenValue > 0){
        this.displayValue = "logged in as: " + this.userName + this.token.token;
        this.isLoggedIn = true;
      }
      else
        this.displayValue = "No user: " + this.userName;
    }
    else
      this.displayValue = "Already logged in as: " + this.userName;
  }
  logOut(){
    if(this.isLoggedIn){
      this.userName = '';
      this.displayValue = "You are no longer logged in"
      this.tokenValue = 0;
      this.isLoggedIn = false;
    }
    else
      this.displayValue = "You are not logged in"
  }

  createAccount(){
    if(!this.isLoggedIn && this.userName != ''){
      this.displayValue = "TODO"
    }
  }

  deleteAccount(){
    if(this.isLoggedIn){
      this.displayValue = "TODO"
    }
  }

  getToken(){
    if(this.isLoggedIn)
      return this.token.token;
    else
      return 0;
  }

}
