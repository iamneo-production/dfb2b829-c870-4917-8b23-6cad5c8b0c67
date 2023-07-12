import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  errorMessage: string = '';

  constructor(private http: HttpClient, private formBuilder: FormBuilder) {
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
          // Handle success response
        },
        (error: HttpErrorResponse) => {
          console.error(error);
          if (error.status === 200) {
            console.log(error.error); // Assuming the success message is returned in the error.error field
            // Handle success response
          } else {
            this.errorMessage = 'An unexpected error occurred. Please try again later.';
          }
        }
      );
  }
}
