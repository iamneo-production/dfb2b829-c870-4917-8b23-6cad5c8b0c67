import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  amount : number=0;
  paymentDate : string='';

  constructor(private http: HttpClient){}
  
  submitPaymentForm(form:NgForm){
    const loanId = UserAuthService.get
  }
}

