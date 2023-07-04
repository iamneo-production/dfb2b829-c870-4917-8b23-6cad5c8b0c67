import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserAuthService } from '../_services/user-auth.service';
import { MatDatepickerControl, MatDatepickerPanel } from '@angular/material/datepicker';
import { MatDialog } from '@angular/material/dialog';
import { url } from '../config';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  // loan_id:  number=0;
  amount : number=0;
  paymentDate : string='';
  
  messageState: string='';
  

  constructor(private http: HttpClient, private dialog: MatDialog){}
  
  submitPaymentForm(form:NgForm){
    if (form.valid) {
      const paymentData = {
        // loan_id:this.loan_id,
        amount:this.amount,
        paymentDate:this.paymentDate
      };
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const options = { headers: headers };
      this.http.post(`${url}/payment`, paymentData, options)
        .subscribe((res) => {
          console.log(res);
          this.openDialog('Success', 'Payment done successfully');
          form.reset();
        },
        (err) => {
          console.log(err);
          this.openDialog('Error', 'An error occurred while doing payment');
        });
    }
    
  }
  openDialog(title: string, message: string) {
    this.messageState = title; // Set the messageState based on the title
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message }
    });
  }
}

