import { Component, Injectable, OnInit } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { UserAuthService } from '../_services/user-auth.service';

import { MatDialog } from '@angular/material/dialog';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';
import { Router } from '@angular/router';

interface Payment {
  loan_id: number;
  amount: number;
  paymentDate: string|null;
  status:string;
  [key: string]: any;
}

@Component({
  selector: 'app-user-payment',
  templateUrl: './user-payment.component.html',
  styleUrls: ['./user-payment.component.css']
})

export class UserPaymentComponent implements OnInit {
  payments: Payment[] = [];
  loan_id: number = 0;
  paymentAmount: number = 0;
  paymentDate: string ='';
  filteredPayments: Payment[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe,
    public userAuthService: UserAuthService,
    private dialog: MatDialog,
    private router: Router
  ) {}
  openPaymentDialog(payment: any) {
    const dialogRef = this.dialog.open(PaymentDialogComponent, {
      data: payment
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'confirm') {
        // Perform payment confirmation logic here
        payment.status = 'Completed'; // Update the payment status
  
        // Send an HTTP request to update the payment status on the backend
        const url = `http://127.0.0.1:8080/payment/updateThePayment/${payment.id}`;
        this.http.put(url, payment).subscribe(
          () => {
            console.log('Payment confirmed!');
            this.router.navigate(['/userHome']); 
            
          },
          (error) => {
            console.error('Failed to update payment status:', error);
          }
        );
      } else if (result === 'cancel') {
        // Perform payment cancellation logic here
        console.log('Payment cancelled.');
      }
    });
  }
  ngOnInit() {
    this.getPaymentsByLoanId();
  }
  
  

  getPaymentsByLoanId() {
    const userDetails=this.userAuthService.getUserdetails()
    this.loan_id=userDetails.loan_id;
    // Assuming you have a way to get the loan ID
    // this.loan_id = this.loan_id; // Replace with the actual loan ID

    const url = `http://127.0.0.1:8080/payment/all`;
    this.http.get<Payment[]>(url).subscribe(
      (response) => {
        this.payments = response;
        this.formatPaymentDate();
        this.filteredPayments = [...this.payments];
        console.log(response);
      },
      (error) => {
        console.error('Failed to fetch payments:', error);
      }
    );
  }
  

  private formatPaymentDate() {
    if (this.payments) {
      this.payments.forEach((payment) => {
        payment.paymentDate = this.datePipe.transform(payment.paymentDate, 'yyyy-MM-dd');
      });
    }
  }

  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();

    if (searchTerm) {
      this.filteredPayments = this.payments.filter((payment: Payment) =>
        String(payment.loan_id).toLowerCase().includes(searchTerm) ||
        String(payment.amount).toLowerCase().includes(searchTerm) ||
        String(payment.paymentDate).toLowerCase().includes(searchTerm)
      );
    } else {
      this.filteredPayments = [...this.payments];
    }
  }

  sortTable(field: string) {
    if (field === this.activeField) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.activeField = field;
      this.sortDirection = 'asc';
    }

    this.filteredPayments.sort((a, b) => {
      const valueA = a[field];
      const valueB = b[field];

      if (typeof valueA === 'string' && typeof valueB === 'string') {
        return this.sortDirection === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
      } else {
        return this.sortDirection === 'asc' ? valueA - valueB : valueB - valueA;
      }
    });
  }
}
