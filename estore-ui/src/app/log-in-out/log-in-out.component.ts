
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

  private temp = this.userName;

  private MIN = 3;

  getUser(user:string){
    if(!this.isLoggedIn){
      this.temp = user;
      this.updateDisplay("User Name: " + this.temp);
    }
  }

  hover() {
    this.userName = this.temp;
    this.accountService.logIn(this.userName).subscribe(value => {this.token = value as Token});
  }

  logIn(){
    if(!this.isLoggedIn){
      if(this.token != null)
        this.tokenValue = this.token.token;
      if(this.tokenValue != 0){
        this.updateDisplay("logged in as: " + this.userName + " token: " + this.tokenValue);
        this.isLoggedIn = true;
        this.update();
      }
      else
        this.updateDisplay("No user: " + this.userName);
    }
    else
      this.updateDisplay("Already logged in as: " + this.userName + " token: " + this.tokenValue);
  }

  logOut(){
    if(this.isLoggedIn){
      this.updateDisplay("You are no longer logged in");
      this.tokenValue = 0;
      this.userName = '';
      this.isLoggedIn = false;
      this.token.token = 0;
      this.update();
    }
    else
      this.updateDisplay("You are not logged in");
  }

  createAccount(){
    if(!this.isLoggedIn){
      if(this.userName.length > this.MIN){
        if(this.accountService.createAccount(this.userName)){
          this.updateDisplay("Account created!");
          this.accountService.logIn(this.userName).subscribe(value => {this.token = value as Token});
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
        this.updateDisplay("failed to delete account");
    }
    else
      this.updateDisplay("log in to delete account");
  }

  loggedInAsCustomer(){
    return (AccountService.getToken() != 0 && AccountService.getToken() != 92668751);
  }

  update(){
    AccountService.setToken(this.tokenValue);
    AccountService.setUser(this.userName);
    AccountService.setLogStatus(this.isLoggedIn);
  }

  updateDisplay(value : string){
    this.displayValue = value;
  }

  loginStatus () {
    return (AccountService.isLoggedIn());
  }
}
