import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-apply-loan-form',
  templateUrl: './apply-loan-form.component.html',
  styleUrls: ['./apply-loan-form.component.css']
})
export class ApplyLoanFormComponent {

  constructor(private http: HttpClient) {}

  submitLoanForm(form: NgForm) {
    if (form.valid) {
      // Perform post request
      const loanData = {
        loanAmount: form.value.loanAmount,
        loanApplyDate: form.value.loanApplyDate,
        purpose: form.value.purpose,
        loanType: form.value.loanType,
      };
      //loanData should be request body 

      const  headers = { 'content-type': 'application/json'}
      const body=JSON.stringify(loanData);
      console.log(body)
      this.http.post('http://localhost:8080/loan-applications', body,{'headers':headers})
      .subscribe
      (res => console.log(res),
      err => console.log(err));
      alert('Loan application submitted successfully');
      form.reset();
    } else {
      alert('Please enter valid details');
    }
  }
}