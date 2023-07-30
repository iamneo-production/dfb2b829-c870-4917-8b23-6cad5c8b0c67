import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.css']
})
export class SetPasswordComponent {
  setPasswordForm: FormGroup;
  errorMessage: string = '';
  email: string = '';

  constructor(private http: HttpClient, private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router) {
    this.setPasswordForm = this.formBuilder.group({
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
  }

  onSubmit() {
    if (this.setPasswordForm.invalid) {
      // Handle invalid form submission
      return;
    }

    if (this.setPasswordForm.value.newPassword !== this.setPasswordForm.value.confirmPassword) {
      // Passwords do not match, handle error
      return;
    }

    const newPassword = this.setPasswordForm.value.newPassword;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'newPassword': newPassword // Include the newPassword header
    });

    this.http.put('http://localhost:8080/otp/set-password', null, {
      params: { email: this.email },
      headers: headers,
      responseType: 'text'
    }).subscribe(
      response => {
        console.log(response);
        // Handle success response
        this.router.navigate(['/login']); // Redirect to login page
      },
      (error: HttpErrorResponse) => {
        console.error(error);
        if (error.status === 200) {
          console.log(error.error); // Assuming the success message is returned in the error.error field
          // Handle success response
          this.router.navigate(['/login']); // Redirect to login page
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again later.';
        }
      }
    );
  }
}
