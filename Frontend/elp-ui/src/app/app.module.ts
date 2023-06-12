import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
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
import { LoginComponent } from './login/login.component';



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
    LoginComponent
    
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
    MatIconModule,
    // FlexLayoutModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatInputModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatChipsModule,

    MatSnackBarModule,
    MatCardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
