import { Component } from '@angular/core';
import { AccountService } from './account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'CardSharks';

  isAdmin(){
    return (AccountService.getToken()==92668751);
  }

  loggedIn() {
    return (AccountService.isLoggedIn())
  }

}

