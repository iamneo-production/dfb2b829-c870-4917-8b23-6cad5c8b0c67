import { Component, OnInit } from '@angular/core';
import { LoanService } from '../loan.service';
import { UserAuthService } from '../_services/user-auth.service';
<<<<<<< HEAD

=======
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967

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

<<<<<<< HEAD
  constructor(private loanService: LoanService,public userAuthService: UserAuthService) {}

  ngOnInit() {
=======
  constructor(private loanService: LoanService, public userAuthService: UserAuthService) {}

  ngOnInit() {
    const userDetails = this.userAuthService.getUserdetails();
    this.userId = userDetails.id;
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
    this.getLoansByUserId(this.userId);
  }

  getLoansByUserId(userId: number) {
<<<<<<< HEAD
    const userDetails=this.userAuthService.getUserdetails()
    this.userId=userDetails.id;
    this.loanService.getLoansByUserId(this.userId).subscribe(
=======
    this.loanService.getLoansByUserId(userId).subscribe(
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
      (loans: any[]) => {
        this.loans = loans;
        this.filteredLoans = loans;
      },
      (error) => {
        console.error('Failed to fetch loans:', error);
      }
    );
  }
<<<<<<< HEAD
  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();
  
    this.filteredLoans = this.loans.filter(loan =>
      String(loan.id).toLowerCase().includes(searchTerm) ||
      String(loan.user_id).toLowerCase().includes(searchTerm) ||
=======

  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();

    this.filteredLoans = this.loans.filter(loan =>
      String(loan.id).toLowerCase().includes(searchTerm) ||
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
      String(loan.loanAmount).toLowerCase().includes(searchTerm) ||
      String(loan.interestRate).toLowerCase().includes(searchTerm) ||
      String(loan.startDate).toLowerCase().includes(searchTerm) ||
      String(loan.endDate).toLowerCase().includes(searchTerm)
    );
<<<<<<< HEAD
  
    console.log('Filtered Loans:', this.filteredLoans);
  }
=======
  }

>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
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
<<<<<<< HEAD
}
=======

  generateReport(loan: any) {
    // Generate report for the loan
    console.log('Generating report for loan:', loan);
  }

  downloadLoan(loan: any) {
    // Download loan details
    console.log('Downloading loan:', loan);
  }
}
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
