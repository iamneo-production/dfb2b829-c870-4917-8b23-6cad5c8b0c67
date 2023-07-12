
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  errorMessage: string = '';

  constructor(private http: HttpClient, private formBuilder: FormBuilder,private snackBar: MatSnackBar) {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.forgotPasswordForm.invalid) {
      // Handle invalid form submission
      return;
    }

    const { email } = this.forgotPasswordForm.value;
    const params = { email };
    const options = { responseType: 'text' as 'json' };

    this.http.put('http://localhost:8080/otp/forgot-password', null, { params, ...options })
  .subscribe(
    response => {
      console.log(response);
      this.snackBar.open(response.toString(), 'Close',{ duration: 3000 }); // Convert response to string and display success message
    },
    (error: HttpErrorResponse) => {
      console.error(error);
      if (error.status === 200) {
        this.snackBar.open(error.error.toString(), 'Close',{ duration: 3000 }); // Convert error message to string and display success message
      } else {
        this.errorMessage = 'An unexpected error occurred. Please try again later.';
        this.snackBar.open(this.errorMessage, 'Close',{ duration: 3000 }); // Display generic error message
      }
    }
  );

  }
}

