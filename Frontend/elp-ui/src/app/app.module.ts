import { NgModule } from '@angular/core';

import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';



import {NgxFileDropModule} from "ngx-file-drop";
import {MatButtonModule} from "@angular/material/button";
import {HeaderComponent} from './header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";

// import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {MatChipsModule} from "@angular/material/chips";

import {MatSnackBarModule} from "@angular/material/snack-bar";

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatDialogModule} from "@angular/material/dialog";


import { HomeComponent } from './home/home.component';
import { ApplyLoanFormComponent } from './apply-loan-form/apply-loan-form.component';
import { DialogComponent } from './dialog/dialog.component';
import { LoansComponent } from './loans/loans.component';
import { ProfileComponent } from './profile/profile.component';
import { MatCardModule } from '@angular/material/card';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { SignupComponent } from './signup/signup.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './_auth/auth.guard';
import { AuthInterceptor } from './_auth/auth.interceptor';
import { UserService } from './_services/user.service';
import { LoginComponent } from './login/login.component';

import { PaymentComponent } from './payment/payment.component';

import { MatListModule } from '@angular/material/list';

import { LogoutConfirmationDialogComponent } from './logout-confirmation-dialog/logout-confirmation-dialog.component';
import { LoanDetailsComponent } from './loan-details/loan-details.component';
import { AddLoanComponent } from './add-loan/add-loan.component';
import { AdminLoanApplicationComponent } from './admin-loan-application/admin-loan-application.component';
import { AdminLoansComponent } from './admin-loans/admin-loans.component';


import { DatePipe } from '@angular/common';
import { PaymentDialogComponent } from './payment-dialog/payment-dialog.component';
import { UserPaymentComponent } from './user-payment/user-payment.component';
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
import { VerifyAccountComponent } from './verify-account/verify-account.component';
import { SetPasswordComponent } from './set-password/set-password.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ContactComponent } from './contact/contact.component';
import { ContactRecordComponent } from './contact-record/contact-record.component';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { AddLoanUserIdComponent } from './add-loan-user-id/add-loan-user-id.component'; 


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    ApplyLoanFormComponent,
    DialogComponent,
    LoansComponent,
    ProfileComponent,
    AdminHomeComponent,
    UserComponent,
    AdminComponent,
    ForbiddenComponent,
    LoginComponent,
    PaymentComponent,
    LoanDetailsComponent,
    AddLoanComponent,
    AdminLoanApplicationComponent,
    AdminLoansComponent,
    SignupComponent,

    LoanDetailsComponent,
    AddLoanComponent,
    AdminLoanApplicationComponent,
    AdminLoansComponent,
    PaymentDialogComponent,
    UserPaymentComponent,
    PaymentDetailsComponent,

    LogoutConfirmationDialogComponent,
        UserPaymentComponent,
        VerifyAccountComponent,
        SetPasswordComponent,
        ForgotPasswordComponent,
        ContactComponent,
        ContactRecordComponent,
        AddLoanUserIdComponent
    
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatToolbarModule,


    // FlexLayoutModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatIconModule,

    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatChipsModule,

    MatSnackBarModule,
    MatCardModule,
    HttpClientModule,  
    MatListModule,


 
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SlickCarouselModule 

  ],
  providers: [   AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    },
    UserService,
    DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}