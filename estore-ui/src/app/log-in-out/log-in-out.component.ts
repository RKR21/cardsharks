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
  private userName = '';
  private token!: Token;
  private static tokenValue = 0;
  displayValue = "You are not logged in"
  private static isLoggedIn = false;
  getUser(user:string){
    if(user != ''){
      this.userName = user;
      this.displayValue = "User Name: " + this.userName;
      this.accountService.logIn(this.userName).subscribe(value => {this.token = value as Token});
    }
  }

  logIn(){
    if(!LogInOutComponent.isLoggedIn && this.userName != ''){
      if(this.token != null)
        LogInOutComponent.tokenValue = this.token.token;
      if(LogInOutComponent.tokenValue > 0){
        this.displayValue = "logged in as: " + this.userName + "token: " + this.token.token;
        LogInOutComponent.isLoggedIn = true;
      }
      else
        this.displayValue = "No user: " + this.userName;
    }
    else
      this.displayValue = "Already logged in";
  }
  logOut(){
    if(LogInOutComponent.isLoggedIn){
      this.displayValue = "You are no longer logged in"
      LogInOutComponent.tokenValue = 0;
      LogInOutComponent.isLoggedIn = false;
    }
    else
      this.displayValue = "You are not logged in"
  }

  createAccount(){
    if(!LogInOutComponent.isLoggedIn && this.userName != ''){
      this.displayValue = "TODO"
    }
  }

  deleteAccount(){
    if(LogInOutComponent.isLoggedIn){
      this.displayValue = "TODO"
    }
  }

  static getToken(){
    return LogInOutComponent.tokenValue;
  }
  static isLogIn(){
    return LogInOutComponent.isLoggedIn;
  }
}
