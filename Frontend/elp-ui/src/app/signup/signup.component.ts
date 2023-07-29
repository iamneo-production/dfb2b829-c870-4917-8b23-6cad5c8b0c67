
import { Component } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.signupForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      address: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]]
    });
  }

  onSubmit() {
    if (this.signupForm.invalid) {
      // Handle invalid form submission
      return;
    }

    const userData = this.signupForm.value;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers: headers, responseType: 'text' as 'json' };

    this.http.post('http://localhost:8080/otp/register', userData, options)
      .subscribe(
        (response: any) => { // Specify the response type as 'any'
          console.log(response);
          this.snackBar.open(response, 'Close', {
            duration: 3000
          });
          // Handle success response
          this.router.navigate(['/verify-account'], { queryParams: { email: userData.email } }); // Navigate to verify-account page with email as a query parameter
        },
        (error: HttpErrorResponse) => {
          console.error(error);
          let errorMessage = 'An unexpected error occurred. Please try again';
          if (error.status === 200) {
            errorMessage = error.error; // Assuming the error message is returned in the error.error field
          }
          this.snackBar.open(errorMessage, 'Close', {
            duration: 3000
          });
        }
      );
  }
}
