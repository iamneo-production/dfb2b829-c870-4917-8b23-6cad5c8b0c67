import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { url } from '../config';

interface LoanApplication {
  id: number;
  user_id: number;
  purpose: string;
  loanAmount: number;
  status: string;
  applicationDate: string | null;
  isApproved: boolean;
  isRejected: boolean;
  [key: string]: any;
}

interface Loan extends LoanApplication {
  startDate: string;
  endDate: string;
  interestRate: number;
}

@Component({
  selector: 'app-admin-loan-application',
  templateUrl: './admin-loan-application.component.html',
  styleUrls: ['./admin-loan-application.component.css'],
})
export class AdminLoanApplicationComponent implements OnInit {
  loanApplications: LoanApplication[] = [];
  selectedLoanAmount: number = 0;
  selectedStartDate: string = '';
  selectedEndDate: string = '';
  selectedInterestRate: number = 0;
  selectedLoanApplication: LoanApplication | null = null;
  filteredLoans: LoanApplication[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  @ViewChild('loanApprovalDialog') loanApprovalDialog!: TemplateRef<any>;

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    const status = 'applied';
    this.getLoanApplicationsByStatus(status);
    
  }

  getLoanApplicationsByStatus(status: string): void {
    const apiUrl = `${url}/loan-applications?status=${status}`;
    this.http.get<LoanApplication[]>(apiUrl).subscribe(
      (res: LoanApplication[]) => {
        this.loanApplications = res;
        this.formatApplicationDate();
        this.filteredLoans = [...this.loanApplications]; 
      },
      (err: any) => {
        console.log(err);
      }
    );
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
  
    this.loanApplications.sort((a, b) => {
      const valueA = a[field];
      const valueB = b[field];
  
      if (typeof valueA === 'string' && typeof valueB === 'string') {
        return this.sortDirection === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
      } else {
        return this.sortDirection === 'asc' ? valueA - valueB : valueB - valueA;
      }
    });
  }
  
  

  private formatApplicationDate() {
    if (this.loanApplications) {
      this.loanApplications.forEach((loanApplication) => {
        loanApplication.applicationDate = this.datePipe.transform(
          loanApplication.applicationDate,
          'yyyy-MM-dd'
        );
        loanApplication.isApproved = loanApplication.status === 'approved';
        loanApplication.isRejected = loanApplication.status === 'rejected';
        
      });
    }
  }

  approveLoan(loanApplication: LoanApplication): void {
    if (loanApplication.status !== 'approved') {
      const loanId = loanApplication.id; // Get the loan ID

      loanApplication.status = 'approved';
      loanApplication.isApproved = true;
      loanApplication.isRejected = false;
      this.updateLoanApplication(loanId, loanApplication).subscribe(
        (res: LoanApplication) => {
          const index = this.loanApplications.findIndex(
            (loanApp) => loanApp.id === res.id
          );
          if (index !== -1) {
            this.loanApplications[index] = res;
            this.formatApplicationDate();
            this.selectedLoanApplication = loanApplication;
            this.dialog.open(this.loanApprovalDialog);
          }
        },
        (err: any) => {
          console.log(err);
        }
      );
    }
  }

  rejectLoan(loanApplication: LoanApplication): void {
    if (loanApplication.status !== 'rejected') {
      const loanId = loanApplication.id; // Get the loan ID

      loanApplication.status = 'rejected';
      loanApplication.isApproved = false;
      loanApplication.isRejected = true;
      this.updateLoanApplication(loanId, loanApplication).subscribe(
        (res: LoanApplication) => {
          const index = this.loanApplications.findIndex(
            (loanApp) => loanApp.id === res.id
          );
          if (index !== -1) {
            this.loanApplications[index] = res;
            this.formatApplicationDate();
            this.openDialog('Success', 'Loan has been rejected');
          }
        },
        (err: any) => {
          console.log(err);
        }
      );
    }
  }

  updateLoanApplication(loanId: number, updatedLoanApplication: LoanApplication) {
    const apiUrl = `${url}/loan-applications/${loanId}`; // Construct the URL with the loan ID
    return this.http.put<LoanApplication>(apiUrl, updatedLoanApplication);
  }

  addLoan(startDate: string, endDate: string, interestRate: number) {
    if (this.selectedLoanApplication) {
      const loanApplication: LoanApplication = {
        ...this.selectedLoanApplication,
        loanAmount: this.selectedLoanAmount,
      };

      const loan: Loan = {
        ...loanApplication,
        startDate,
        endDate,
        interestRate,
      };

      const addLoanUrl = `${url}/loans`;
      this.http.post<Loan>(addLoanUrl, loan).subscribe(
        (res: Loan) => {
          console.log(res);
          loanApplication.status = 'approved';
          this.updateLoanApplication(loan.id, loanApplication).subscribe(
            (res: LoanApplication) => {
              const index = this.loanApplications.findIndex(
                (loanApp) => loanApp.id === res.id
              );
              if (index !== -1) {
                this.loanApplications[index] = res;
                this.formatApplicationDate();
                this.openDialog('Success', 'Loan added successfully');
              }
            },
            (err: any) => {
              console.log(err);
            }
          );
        },
        (err: any) => {
          console.log(err);
          this.openDialog('Error', 'Failed to add the loan');
        }
      );

      this.dialog.closeAll();
    }
  }

  openDialog(title: string, message: string) {
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message },
    });
  }

  submitLoanApprovalForm() {
    if (this.selectedStartDate && this.selectedEndDate && this.selectedInterestRate) {
      this.addLoan(this.selectedStartDate, this.selectedEndDate, this.selectedInterestRate);
    }
  }

  updateLoanAmount(value: number) {
    if (this.selectedLoanApplication) {
      this.selectedLoanApplication.loanAmount = value;
    }
  }
}
