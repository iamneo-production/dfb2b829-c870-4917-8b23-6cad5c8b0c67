import { Component, OnInit } from '@angular/core';
import { LoanService } from '../loan.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-loans',
  templateUrl: './loans.component.html',
  styleUrls: ['./loans.component.css']
})
export class LoansComponent implements OnInit {
  userId: number = 0; // User ID to fetch loans for
  loans: any[] = [];
  filteredLoans: any[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  constructor(private loanService: LoanService, public userAuthService: UserAuthService) {}

  ngOnInit() {
    const userDetails = this.userAuthService.getUserdetails();
    this.userId = userDetails.id;
    this.getLoansByUserId(this.userId);
  }

  getLoansByUserId(userId: number) {
    this.loanService.getLoansByUserId(userId).subscribe(
      (loans: any[]) => {
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
      String(loan.startDate).toLowerCase().includes(searchTerm) ||
      String(loan.endDate).toLowerCase().includes(searchTerm)
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

  generateReport(loan: any) {
    // Generate report for the loan
    console.log('Generating report for loan:', loan);
  }

  downloadLoan(loan: any) {
    // Download loan details
    console.log('Downloading loan:', loan);
  }
}
