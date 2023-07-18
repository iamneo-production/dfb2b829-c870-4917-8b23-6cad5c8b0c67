import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';

interface LoanApplication {
  id: number;
  loanAmount: number;
  status: string;
  applicationDate: string | null;
  purpose: string;
  [key: string]: any;
}

@Component({
  selector: 'app-loan-details',
  templateUrl: './loan-details.component.html',
  styleUrls: ['./loan-details.component.css']
})
export class LoanDetailsComponent implements OnInit {
  loanApplications: LoanApplication[] = [];
  userId: number = 3;
  filteredLoans: LoanApplication[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  ngOnInit() {
    this.getLoansByUserId();
  }

  getLoansByUserId() {
    const url = `http://localhost:8080/loan-applications?user=${this.userId}`;
    this.http.get<LoanApplication[]>(url).subscribe(
      (response) => {
        this.loanApplications = response;
        this.formatApplicationDate();
        this.filteredLoans = [...this.loanApplications]; 
      },
      (error) => {
        console.error('Failed to fetch loans:', error);
      }
    );
  }

  private formatApplicationDate() {
    if (this.loanApplications) {
      this.loanApplications.forEach((loanApplication) => {
        loanApplication.applicationDate = this.datePipe.transform(loanApplication.applicationDate, 'yyyy-MM-dd');
      });
    }
  }
  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();
  
    if (searchTerm) {
      this.filteredLoans = this.loanApplications.filter((loanApplication: LoanApplication) =>
        String(loanApplication.id).toLowerCase().includes(searchTerm) ||
        String(loanApplication.purpose).toLowerCase().includes(searchTerm) ||
        String(loanApplication.loanAmount).toLowerCase().includes(searchTerm) ||
        String(loanApplication.status).toLowerCase().includes(searchTerm) ||
        loanApplication.applicationDate?.toLowerCase().includes(searchTerm)

      );
    } else {
      this.filteredLoans = [...this.loanApplications];
    }
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
  
  
}
