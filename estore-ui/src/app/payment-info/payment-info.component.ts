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

  async addPayment(type:string){
    this.accountService.addPayment({type} as Payment);
    console.log("addpayment");
    await(50);//waits for the backend to update before getting list again.
    this.getPayments();
  }

  getPayments(){
    console.log("gt pre");
    this.accountService.getPayments()
    .subscribe(payments => this.payments = payments);
    console.log("gt post");
  }

  delete(payment:Payment):void{
      this.accountService.deletePayment(payment);
      console.log("dleet");
      this.getPayments();
  }

}
