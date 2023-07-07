import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { FormsModule } from '@angular/forms';

import { ReactiveFormsModule} from "@angular/forms";

import {NgxFileDropModule} from "ngx-file-drop";
import {MatButtonModule} from "@angular/material/button";
import {HeaderComponent} from './header/header.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
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
<<<<<<< HEAD
import { ContactComponent } from './contact/contact.component';
import { ContactService } from './contact.service';
=======
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
>>>>>>> 345836c2b602d5c59b604875ba996770c37981ac

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
<<<<<<< HEAD
import { UserPaymentComponent } from './user-payment/user-payment.component';
=======
>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    ApplyLoanFormComponent,
    DialogComponent,
    LoansComponent,
    ProfileComponent,
<<<<<<< HEAD
    ContactComponent
   
=======
    AdminHomeComponent,
    UserComponent,
    AdminComponent,
    ForbiddenComponent,
    LoginComponent,
<<<<<<< HEAD
    PaymentComponent,
=======
>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26
    LoanDetailsComponent,
    AddLoanComponent,
    AdminLoanApplicationComponent,
    AdminLoansComponent,
<<<<<<< HEAD
=======

>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26


    LogoutConfirmationDialogComponent,
        UserPaymentComponent
    
>>>>>>> 345836c2b602d5c59b604875ba996770c37981ac
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgxFileDropModule,
    MatButtonModule,
    MatToolbarModule,
<<<<<<< HEAD
<<<<<<< HEAD
    MatIconModule,
    MatSidenavModule,
    MatListModule,
=======

>>>>>>> 345836c2b602d5c59b604875ba996770c37981ac
=======

>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26
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

  ],
<<<<<<< HEAD
  providers: [ContactService],
=======
  providers: [   AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    },
    UserService,
    DatePipe],
<<<<<<< HEAD
>>>>>>> 345836c2b602d5c59b604875ba996770c37981ac
=======
>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26
  bootstrap: [AppComponent]
})
export class AppModule {
}

