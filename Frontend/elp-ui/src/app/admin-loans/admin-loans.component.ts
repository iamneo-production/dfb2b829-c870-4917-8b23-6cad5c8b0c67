import { Component ,OnInit, TemplateRef, ViewChild} from '@angular/core';
import { LoanService } from '../loan.service';
import { MatDialog } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-admin-loans',
  templateUrl: './admin-loans.component.html',
  styleUrls: ['./admin-loans.component.css']
})
export class AdminLoansComponent implements OnInit{

  @ViewChild('dialogTemplate') dialogTemplate!: TemplateRef<any>;
  
  selectedStatus: string = 'Approved';
  statuses: string[] = ['Approved', 'Rejected'];
  loans: any[] = [];
  filteredLoans: any[] = [];
  searchTerm: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  activeField: string = '';

  constructor(private loanService: LoanService, private dialog: MatDialog,private datePipe: DatePipe) { }

  ngOnInit() {
    this.getLoansByStatus(this.selectedStatus);
  }
 

  getLoansByStatus(status: string) {
    this.loanService.getLoansByStatus(status).subscribe(
      (loans: any[]) => {
        this.loans = loans;
        this.filteredLoans = loans;
      },
      (error) => {
        console.error('Failed to fetch loans:', error);
      }
    );
  }

  onStatusChange() {
    this.getLoansByStatus(this.selectedStatus);
  }
  onSearch() {
    const searchTerm = this.searchTerm.toLowerCase().trim();
  
    this.filteredLoans = this.loans.filter(loan =>
      String(loan.id).toLowerCase().includes(searchTerm) ||
      String(loan.user_id).toLowerCase().includes(searchTerm) ||
      String(loan.loanAmount).toLowerCase().includes(searchTerm) ||
      String(loan.interestRate).toLowerCase().includes(searchTerm) ||
      String(loan.startDate).toLowerCase().includes(searchTerm) ||
      String(loan.endDate).toLowerCase().includes(searchTerm)
    );
  
    console.log('Filtered Loans:', this.filteredLoans);
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
  
  
  
  editLoan(loan: any) {
    const formattedStartDate = this.datePipe.transform(loan.startDate, 'yyyy-MM-dd');
    const formattedEndDate = this.datePipe.transform(loan.endDate, 'yyyy-MM-dd');
  
    const dialogRef = this.dialog.open(this.dialogTemplate, {
      width: '400px',
      data: { message: '', loan: { ...loan, startDate: formattedStartDate, endDate: formattedEndDate } }
  
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const updatedLoan = result.loan;
        this.loanService.updateLoan(updatedLoan.id, updatedLoan).subscribe(
          (response) => {
            console.log('Loan updated successfully:', response);
            const index = this.loans.findIndex(l => l.id === updatedLoan.id);
            if (index !== -1) {
              this.loans[index] = updatedLoan;
              this.filteredLoans[index] = updatedLoan;
            }
          },
          (error) => {
            console.error('Failed to update loan:', error);
            dialogRef.componentInstance.message = 'Failed to update loan.';
            dialogRef.componentInstance.messageState = 'Error';
          }
        );
      }
    });
  }
  

  deleteLoan(loan: any): void {
    const confirmation = confirm('Are you sure you want to delete this loan?');
    if (confirmation) {
      this.loanService.deleteLoan(loan.id).subscribe(
        () => {
          console.log('Loan deleted successfully');
          // Remove the deleted loan from the loans and filteredLoans arrays
          const index = this.loans.findIndex(l => l.id === loan.id);
          if (index !== -1) {
            this.loans.splice(index, 1);
            this.filteredLoans.splice(index, 1);
          }
        },
        (error) => {
          console.error('Failed to delete loan:', error);
        }
      );
    }
  }
}






