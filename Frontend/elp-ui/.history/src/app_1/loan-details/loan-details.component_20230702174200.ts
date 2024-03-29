import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { UserAuthService } from '../_services/user-auth.service';
import { saveAs } from 'file-saver';


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
  userId: number = 0;
  filteredLoans: LoanApplication[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  constructor(private http: HttpClient, private datePipe: DatePipe, public userAuthService: UserAuthService) {}

  ngOnInit() {
    this.getLoansByUserId();
  }


  getLoansByUserId() {
    const userDetails=this.userAuthService.getUserdetails()
    this.userId=userDetails.id;
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
  downloadPdf(loanApplication: LoanApplication) {
    // Prepare the data for the PDF
    const pdfData = {
      id: loanApplication.id,
      loanAmount: loanApplication.loanAmount,
      status: loanApplication.status,
      applicationDate: loanApplication.applicationDate,
      purpose: loanApplication.purpose,
      // Include any other properties you want in the PDF
    };

    // Convert the data to a JSON string
    const json = JSON.stringify(pdfData);

    // Convert the JSON string to a Blob
    const blob = new Blob([json], { type: 'application/json' });

    // Save the Blob as a file
    saveAs(blob, 'loan-details.pdf');
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
