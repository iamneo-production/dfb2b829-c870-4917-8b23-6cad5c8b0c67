import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { UserAuthService } from '../_services/user-auth.service';
import { StringEncryptionService } from '../_services/string-encryption.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

import { PaymentService } from '../payment.service';
import { DialogComponent } from '../dialog/dialog.component';




interface Payment {
  id: number;
  loan_id: number;
  amount: number;
  paymentDate: string | null;
  status: string;
  [key: string]: any;
}

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.css']
})
export class PaymentDetailsComponent implements OnInit {
  payments: Payment[] = [];
  id: number = 0;
  loan_id: number = 0;
  paymentAmount: number = 0;
  paymentDate: string = '';
  status : string = 'Ongoing'
  filteredPayments: Payment[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';
  dialogRef!: MatDialogRef<any>;
  @ViewChild('editDialog') editDialog!: TemplateRef<any>;
 
 

  constructor(
    private paymentService : PaymentService,
    private http: HttpClient,
    private datePipe: DatePipe,
    public userAuthService: UserAuthService,
    private encryptionService: StringEncryptionService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getPaymentsByPaymentId();
  }

  getPaymentsByPaymentId() {
    // Assuming you have a way to get the payment ID
    const paymentId = this.id;

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
        String(payment.paymentDate).toLowerCase().includes(searchTerm) ||
        String(payment.status).toLowerCase().includes(searchTerm)
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

  editPayment(payment: Payment) {
    this.dialogRef = this.dialog.open(this.editDialog, {
      width: '400px',
      data: payment
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updatePayment(result);
      }
    });
  }
  closeEditDialog() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }

  deletePayment(payment: Payment) {
    const confirmation = confirm('Are you sure you want to delete this payment?');

    if (confirmation) {
      const url = `http://127.0.0.1:8080/payment/${payment.id}`;

      this.http.delete(url).subscribe(
        () => {
          // Remove the deleted payment from the payments array
          this.payments = this.payments.filter((p) => p.id !== payment.id);
          this.filteredPayments = [...this.payments];
          console.log('Payment deleted successfully');
        },
        (error) => {
          console.error('Failed to delete payment:', error);
        }
      );
    }
  }
  updatePayment(updatedPayment: Payment){
    
      if (this.status === 'Completed') {
        updatedPayment.status = 'Completed';
      } else {
        updatedPayment.status = 'Ongoing';
      }
    const id = updatedPayment.id;
    this.paymentService.updatePayment(id,updatedPayment).subscribe(
      (resp) => {
        console.log(resp);
        this.openDialog('Success', 'Payment details updated successfully');
      },
      (err) => {
        console.log(err);
        this.openDialog('Error', 'Failed to update the details');
      }
    )
  }
  openDialog(title: string, message: string) {
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message }
    });
  }
}