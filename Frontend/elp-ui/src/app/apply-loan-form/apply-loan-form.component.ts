import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';

import { url } from '../config';

@Component({
  selector: 'app-apply-loan-form',
  templateUrl: './apply-loan-form.component.html',
  styleUrls: ['./apply-loan-form.component.css']
})
export class ApplyLoanFormComponent {
  loanType: string = '';
  loanAmount: number = 0;
  loanApplyDate: string = '';
  purpose: string = '';
  messageState: string = '';

  constructor(private http: HttpClient, private dialog: MatDialog) {}

  submitLoanForm(form: NgForm) {
    if (form.valid) {
      const loanData = {
        loanAmount: this.loanAmount,
        applicationDate: this.loanApplyDate,
        purpose: this.purpose,
        loanType: this.loanType,
        status: 'Applied',
        user_id:'10'
      };

      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const options = { headers: headers };

      this.http.post(`${url}/loan-applications`, loanData, options)
        .subscribe(
          (res) => {
            console.log(res);
            this.openDialog('Success', 'Loan application submitted successfully');
            form.reset();
          },
          (err) => {
            console.log(err);
            this.openDialog('Error', 'An error occurred while submitting the loan application');
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
