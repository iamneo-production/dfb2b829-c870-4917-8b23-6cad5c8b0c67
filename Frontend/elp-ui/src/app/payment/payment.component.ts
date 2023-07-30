import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { UserAuthService } from '../_services/user-auth.service';
import { url } from '../config/url'; 

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  loan_id: number = 0;
  paymentAmount: number = 0;
  paymentDate: string = '';
  status: string="Ongoing";
  messageState: string = '';

  constructor(private http: HttpClient, private dialog: MatDialog, public userAuthService: UserAuthService) {}

  submitPaymentForm(form: NgForm) {
    const userDetails = this.userAuthService.getUserdetails();
    const userId = userDetails.id;

    if (form.valid) {
      const paymentData = {
        loan_id: this.loan_id,
        amount: this.paymentAmount,
        paymentDate: this.paymentDate,
        status: "Ongoing"
      };

      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const options = { headers: headers };

      this.http.post(`${url}payment`, paymentData, options).subscribe(
        (res) => {
          console.log(res);
          this.openDialog('Success', 'Payment added successfully');
          form.reset();
        },
        (err) => {
          console.log(err);
          this.openDialog('Error', 'An error occurred while adding the payment');
        }
      );
    } else {
      this.openDialog('Validation Error', 'Please enter valid details');
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

