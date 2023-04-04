
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

  private token!: Token;
  displayValue = "User Name: " + AccountService.getUser();

  private userName = AccountService.getUser();
  private tokenValue = AccountService.getToken();
  private isLoggedIn = AccountService.isLoggedIn();

  private MIN = 4;

  getUser(user:string){
    if(!this.isLoggedIn){
      this.userName = user;
      this.updateDisplay("User Name: " + this.userName);
      if(user.length > this.MIN && user != AccountService.getUser())
        this.accountService.logIn(this.userName).subscribe(value => {this.token = value as Token});
    }
  }

  logIn(){
    if(!this.isLoggedIn){
      if(this.token != null)
        this.tokenValue = this.token.token;
      if(this.tokenValue != 0){
        this.updateDisplay("logged in as: " + this.userName + " token: " + this.token.token);
        this.isLoggedIn = true;
        this.update();
      }
      else
        this.updateDisplay("No user: " + this.userName);
    }
    else
      this.updateDisplay("Already logged in");
  }

  logOut(){
    if(this.isLoggedIn){
      this.updateDisplay("You are no longer logged in");
      this.tokenValue = 0;
      this.isLoggedIn = false;
      this.update();
    }
    else
    this.updateDisplay("You are not logged in");
  }

  createAccount(){
    if(!this.isLoggedIn){
      if(this.userName.length > this.MIN){
        if(this.accountService.createAccount(this.userName)){
          this.logIn();
          this.updateDisplay("Account created!");
        }
        else
          this.updateDisplay("failed to create account");
      }
      else
        this.updateDisplay("user names need to be longer than " + this.MIN + " characters");
    }
    else
    this.updateDisplay("log out to create account");
  }

  deleteAccount(){
    if(this.isLoggedIn){
      if(this.accountService.deleteAccount()){
        this.logOut();
        this.updateDisplay("Account deleted!");
      }
      else
        this.updateDisplay("failed to create account");
    }
    else
      this.updateDisplay("log in to create account");
  }

  loggedInAsCustomer(){
    if(AccountService.getToken() != 0 && AccountService.getToken() != 92668751){
      return true;
    } 
    
    return false;

  }

  update(){
    AccountService.setToken(this.tokenValue);
    AccountService.setUser(this.userName);
    AccountService.setLogStatus(this.isLoggedIn);
  }

  updateDisplay(value : string){
    this.displayValue = value;
  }
}
