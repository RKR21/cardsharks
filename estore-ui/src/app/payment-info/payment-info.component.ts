import { Component } from '@angular/core';
import { AccountService } from '../account.service';
import { Payment } from '../payment';

@Component({
  selector: 'app-payment-info',
  templateUrl: './payment-info.component.html',
  styleUrls: ['./payment-info.component.css']
})
export class PaymentInfoComponent {
  constructor(private accountService: AccountService){}
  payments: Payment[] = []

  ngOnInit(): void {
    this.getPayments();
  }

  addPayment(type:string){
    
    this.accountService.addPayment({type} as Payment);
    
  }

  getPayments(){
    this.accountService.getPayments()
    .subscribe(payments => this.payments = payments);
  }

  delete(payment:Payment):void{
      this.accountService.deletePayment(payment);

  }

}
