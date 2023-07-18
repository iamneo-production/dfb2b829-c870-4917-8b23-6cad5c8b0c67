import { Component, OnDestroy } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, timer } from 'rxjs';
import { switchMap, take } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-verify-account',
  templateUrl: './verify-account.component.html',
  styleUrls: ['./verify-account.component.css']
})
export class VerifyAccountComponent implements OnDestroy {
  verifyForm: FormGroup;
  email: string = '';
  showResend: boolean = true;
  remainingTime: number = 0;
  timerSubscription: Subscription | undefined;

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.verifyForm = this.formBuilder.group({
      otp: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
    this.startTimer();
  }

  ngOnDestroy() {
    this.stopTimer();
  }

  startTimer() {
    this.remainingTime = 60;
    this.timerSubscription = timer(0, 1000)
      .pipe(
        take(this.remainingTime + 1),
        switchMap(() => {
          if (this.remainingTime > 0) {
            this.remainingTime--;
            if (this.remainingTime === 0) {
              this.showResend = true;
            }
          }
          return [];
        })
      )
      .subscribe();
  }

  stopTimer() {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }

  onSubmit() {
    if (this.verifyForm.invalid) {
      // Handle invalid form submission
      return;
    }

    const { otp } = this.verifyForm.value;
    const params = { email: this.email, otp };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const options = { headers: headers, responseType: 'text' as 'json' };

    this.http.put('http://localhost:8080/otp/verify-account', null, { params, ...options })
    .subscribe(
      response => {
        this.showSnackBar(response.toString()); // Convert response to string and display success message
        this.router.navigate(['/login']); // Redirect to login page
      },
      (error: HttpErrorResponse) => {
        console.error(error);
        if (error.status === 200) {
          this.showSnackBar(error.error.toString()); // Convert error.error to string and display success message from error response
          this.router.navigate(['/login']); // Redirect to login page
        } else {
          this.showSnackBar('An unexpected error occurred. Please try again later.'); // Display error message
        }
      }
    );
}
resendOTP() {
  this.showResend = false;
  this.remainingTime = 60;
  this.startTimer();

  const params = { email: this.email };
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  const options = { headers: headers, responseType: 'text' as 'json' };

  this.http.put('http://localhost:8080/otp/regenerate-otp', null, { params, ...options })
    .subscribe(
      response => {
        this.showSnackBar(response.toString()); // Convert response to string and display success message
      },
      (error: HttpErrorResponse) => {
        console.error(error);
        if (error.status === 200) {
          const errorMessage = error.error.toString(); // Convert error.error to string
          if (errorMessage.includes('OTP regeneration limit exceeded')) {
            this.showSnackBar(errorMessage); // Display error message from backend
          } else {
            this.showSnackBar('An unexpected error occurred. Please try again later.'); // Display generic error message
          }
        } else {
          this.showSnackBar('An unexpected error occurred. Please try again later.'); // Display error message
        }
      }
    );
}


  showSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000 // Display the snack bar for 3 seconds
    });
  }
}

