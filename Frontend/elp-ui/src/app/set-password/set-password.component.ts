import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.css']
})
export class SetPasswordComponent implements OnInit {
  setPasswordForm: FormGroup;
  errorMessage: string = '';
  email: string = '';
  token: string = '';

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.setPasswordForm = this.formBuilder.group({
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      this.email = params['email'];
    });
  }

  onSubmit() {
    if (this.setPasswordForm.invalid) {
      return;
    }

    if (this.setPasswordForm.value.newPassword !== this.setPasswordForm.value.confirmPassword) {
      return;
    }

    const newPassword = this.setPasswordForm.value.newPassword;

    if (!this.token) {
      console.error('Token not found in URL.');
      return;
    }

    // Prepare the headers with the token and newPassword
    const headers = new HttpHeaders({
      'Token': this.token,
      'NewPassword': newPassword,
      'Email': this.email
    });

    const url = 'http://localhost:8080/otp/set-password';

    // Send the request with headers
    this.http.post<string>(url, {}, { headers: headers }).subscribe(
      response => {
        console.log(response);
        this.snackBar.open(response, 'Close', { duration: 3000 });
        this.router.navigate(['/login']);
      },
      (error: HttpErrorResponse) => {
        console.error(error);
        if (error.status === 200) {
          console.log(error.error);
          this.snackBar.open(error.error, 'Close', { duration: 3000 });
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again later.';
          this.snackBar.open(this.errorMessage, 'Close', { duration: 3000 });
        }
      }
    );
  }
}
