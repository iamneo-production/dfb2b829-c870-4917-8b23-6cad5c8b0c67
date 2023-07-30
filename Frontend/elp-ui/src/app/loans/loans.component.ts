import { Component, OnInit } from '@angular/core';
import { LoanService } from '../loan.service';
import { UserAuthService } from '../_services/user-auth.service';
import { StringEncryptionService } from '../_services/string-encryption.service';

interface Loan {
  id: number;
  loanAmount: number;
  interestRate: number;
  startDate: string;
  endDate: string;
  [key: string]: any; // Add an index signature to allow arbitrary string keys
}

@Component({
  selector: 'app-loans',
  templateUrl: './loans.component.html',
  styleUrls: ['./loans.component.css']
})
export class LoansComponent implements OnInit {
  userId: number = 0; // User ID to fetch loans for
  loans: Loan[] = [];
  filteredLoans: Loan[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  constructor(
    private loanService: LoanService,
    public userAuthService: UserAuthService,
    private encryptionService: StringEncryptionService
  ) {}

  ngOnInit() {
    const userDetails = this.userAuthService.getUserdetails();
    this.userId = userDetails.id;
    this.getLoansByUserId(this.userId);
  }

  getLoansByUserId(userId: number) {
    this.loanService.getLoansByUserId(userId).subscribe(
      (loans: Loan[]) => {
        this.loans = loans;
        this.filteredLoans = loans;
      },
      (error) => {
        console.error('Failed to fetch loans:', error);
      }
    );
  }

  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();

    this.filteredLoans = this.loans.filter(loan =>
      String(loan.id).toLowerCase().includes(searchTerm) ||
      String(loan.loanAmount).toLowerCase().includes(searchTerm) ||
      String(loan.interestRate).toLowerCase().includes(searchTerm) ||    
      loan.startDate.toLowerCase().includes(searchTerm) ||
      loan.endDate.toLowerCase().includes(searchTerm)
    );
  }

  sortTable(field: string) {
    if (field === this.activeField) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.activeField = field;
      this.sortDirection = 'asc';
    }

    this.filteredLoans.sort((a, b) => {
      const valueA = a[field];
      const valueB = b[field];

      if (typeof valueA === 'string' && typeof valueB === 'string') {
        return this.sortDirection === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
      } else {
        return this.sortDirection === 'asc' ? valueA - valueB : valueB - valueA;
      }
    });
  }

  generateReport(loan: Loan) {
    // Generate report for the loan
    console.log('Generating report for loan:', loan);
  }

  downloadPdf(loan: Loan) {
    const loanId = loan.id;
    const encodedId = this.encryptionService.encodeString(loanId.toString());
    const url = `http://localhost:8080/loans/download?encodedId=${encodedId}`;

    // Trigger the file download by creating a link and clicking it programmatically
    const link = document.createElement('a');
    link.href = url;
    link.target = '_blank';
    link.click();
  }
}
