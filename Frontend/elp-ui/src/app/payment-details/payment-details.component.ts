import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { UserAuthService } from '../_services/user-auth.service';
import { StringEncryptionService } from '../_services/string-encryption.service';
import { MatDialog } from '@angular/material/dialog';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';

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

  constructor(
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

  editPayment(payment: Payment) {
    // Implement the edit logic here
    // You can open a dialog or navigate to a different page for editing
    console.log('Editing payment:', payment);
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
}
