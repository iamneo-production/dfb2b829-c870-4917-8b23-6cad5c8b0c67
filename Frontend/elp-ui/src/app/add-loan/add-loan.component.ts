import { Component, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { url } from '../config/url'; 
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

interface Loan {
  loanAmount: number;
  startDate: Date;
  endDate: Date;
  interestRate: number;
}

@Component({
  selector: 'app-add-loan',
  templateUrl: './add-loan.component.html',
  styleUrls: ['./add-loan.component.css']
})
export class AddLoanComponent implements AfterViewInit {
  loan: Loan = {
    loanAmount: 0,
    startDate: new Date(),
    endDate: new Date(),
    interestRate: 0
  };

  @ViewChild('loanApprovalDialog') loanApprovalDialog!: TemplateRef<any>;
  dialogRef!: MatDialogRef<any>;

  constructor(private http: HttpClient, private dialog: MatDialog, private route: ActivatedRoute) { }

  ngAfterViewInit() {
    this.route.queryParams.subscribe(params => {
      if (params['showDialog'] === 'true') {
        this.openLoanApprovalDialog();
      }
    });
  }

  openLoanApprovalDialog() {
    this.dialogRef = this.dialog.open(this.loanApprovalDialog, {
      width: '400px'
    });
  }

  closeLoanApprovalDialog() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }

  submitLoanApprovalForm() {
    const addLoanUrl = `${url}loans`;
    this.http.post<Loan>(addLoanUrl, this.loan).subscribe(
      (res: Loan) => {
        console.log(res);
        // Handle loan addition success
        this.openDialog('Success', 'Loan added successfully');
      },
      (err: any) => {
        console.error(err);
        // Handle loan addition failure
        this.openDialog('Error', 'Failed to add the loan');
      }
    );

    this.closeLoanApprovalDialog();
  }

  openDialog(title: string, message: string) {
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message }
    });
  }
}
