import { Component } from '@angular/core';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-payment-info',
  templateUrl: './payment-info.component.html',
  styleUrls: ['./payment-info.component.css']
})
export class PaymentInfoComponent {

  payments: string[] = []


  addPayment(payment:string){

    AccountService.addPayment(payment);
    


  }

}
