import { Component } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;
  errorMessage: string = '';

  constructor(private http: HttpClient, private formBuilder: FormBuilder, private router: Router) {
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
        response => {
          console.log(response);
          // Handle success response
          this.router.navigate(['/verify-account'], { queryParams: { email: userData.email } }); // Navigate to verify-account page with email as a query parameter
        },
        (error: HttpErrorResponse) => {
          console.error(error);
          if (error.status === 200) {
            console.log(error.error); // Assuming the error message is returned in the error.error field
            // Handle success response
            this.router.navigate(['/verify-account'], { queryParams: { email: userData.email } }); // Navigate to verify-account page with email as a query parameter
          } else {
            this.errorMessage = 'An unexpected error occurred. Please try again later.';
          }
        }
      );
  }
}
