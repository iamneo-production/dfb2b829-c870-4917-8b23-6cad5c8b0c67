import { Component, OnInit } from '@angular/core';
import { LoanService } from '../loan.service';

@Component({
  selector: 'app-loans',
  templateUrl: './loans.component.html',
  styleUrls: ['./loans.component.css']
})
export class LoansComponent implements OnInit {
  userId: number = 13; // User ID to fetch loans for
  loans: any[] = [];
  filteredLoans: any[] = [];

  constructor(private loanService: LoanService) { }

  ngOnInit() {
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
}